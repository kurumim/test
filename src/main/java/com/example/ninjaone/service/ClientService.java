package com.example.ninjaone.service;

import com.example.ninjaone.controller.request.ClientRequest;
import com.example.ninjaone.controller.request.DeviceRequest;
import com.example.ninjaone.controller.request.ServiceRequest;
import com.example.ninjaone.controller.response.ClientResponse;
import com.example.ninjaone.exceptions.ValidOperationException;
import com.example.ninjaone.model.ClientEntity;
import com.example.ninjaone.repository.ClientRepository;
import com.example.ninjaone.service.mappers.ClientMapper;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import org.springframework.stereotype.Service;

@Service
public class ClientService
    extends GenericService<
        ClientRequest, ClientResponse, ClientEntity, ClientRepository, ClientMapper> {
  private final ClientRepository clientRepository;

  private final ClientMapper clientMapper;

  public ClientService(final ClientRepository repository, final ClientMapper mapper) {
    super(repository, mapper);
    this.clientRepository = repository;
    this.clientMapper = mapper;
  }

  @Override
  public void validOperation(ClientRequest clientRequest) {}

  public ClientResponse setServices(Long id, Set<Long> serviceIds) {
    final var client =
        clientRepository
            .findById(id)
            .orElseThrow(() -> new ValidOperationException(String.format(NOT_FOUND, id)));
    final var clientResponse = clientMapper.toResponse(client);
    clientResponse.setServices(
        serviceIds.stream()
            .map(
                i -> {
                  final var service = new ServiceRequest();
                  service.setId(i);
                  return service;
                })
            .toList());
    final var c =
        clientMapper.toResponse(
            clientRepository.save(clientMapper.tofromResponseToEntity(clientResponse)));
    calcCost(c);
    CompletableFuture.runAsync(() -> clientRepository.save(clientMapper.tofromResponseToEntity(c)));
    return c;
  }

  public ClientResponse setDevices(Long id, Set<Long> deviceIds) {
    final var client =
        clientRepository
            .findById(id)
            .orElseThrow(() -> new ValidOperationException(String.format(NOT_FOUND, id)));
    final var clientResponse = clientMapper.toResponse(client);
    clientResponse.setDevices(
        deviceIds.stream()
            .map(
                i -> {
                  final var device = new DeviceRequest();
                  device.setId(i);
                  return device;
                })
            .toList());
    final var c =
        clientMapper.toResponse(
            clientRepository.save(clientMapper.tofromResponseToEntity(clientResponse)));
    calcCost(c);
    CompletableFuture.runAsync(() -> clientRepository.save(clientMapper.tofromResponseToEntity(c)));
    return c;
  }

  private void calcCost(ClientResponse clientResponse) {
    Map<String, BigDecimal> countByServiceType = new HashMap<>();
    clientResponse
        .getServices()
        .forEach(
            service -> {
              countByServiceType.put(
                  service.getType(),
                  countByServiceType
                      .getOrDefault(service.getType(), BigDecimal.ZERO)
                      .add(service.getCost()));
            });
    Map<String, BigDecimal> quantityByDeviceType = new HashMap<>();
    clientResponse
        .getDevices()
        .forEach(
            device -> {
              quantityByDeviceType.put(
                  device.getType(),
                  quantityByDeviceType
                      .getOrDefault(device.getType(), BigDecimal.ZERO)
                      .add(BigDecimal.ONE));
            });
    final var totalCost =
        quantityByDeviceType.keySet().stream()
            .map(
                type ->
                    countByServiceType
                        .getOrDefault(type, BigDecimal.ZERO)
                        .multiply(quantityByDeviceType.getOrDefault(type, BigDecimal.ZERO)))
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
    clientResponse.setCost(totalCost);
  }
}
