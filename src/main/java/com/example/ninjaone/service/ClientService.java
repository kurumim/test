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
import java.util.function.Consumer;
import java.util.function.Function;
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
    clientResponse.setServices(serviceIds.stream().map(getLongServiceRequestFunction()).toList());
    final var c =
        clientMapper.toResponse(
            clientRepository.save(clientMapper.tofromResponseToEntity(clientResponse)));
    calcCost(c);
    CompletableFuture.runAsync(() -> clientRepository.save(clientMapper.tofromResponseToEntity(c)));
    return c;
  }

  private Function<Long, ServiceRequest> getLongServiceRequestFunction() {
    return i -> {
      final var service = new ServiceRequest();
      service.setId(i);
      return service;
    };
  }

  public ClientResponse setDevices(Long id, Set<Long> deviceIds) {
    final var client =
        clientRepository
            .findById(id)
            .orElseThrow(() -> new ValidOperationException(String.format(NOT_FOUND, id)));
    final var clientResponse = clientMapper.toResponse(client);
    clientResponse.setDevices(deviceIds.stream().map(getLongDeviceRequestFunction()).toList());
    final var c =
        clientMapper.toResponse(
            clientRepository.save(clientMapper.tofromResponseToEntity(clientResponse)));
    calcCost(c);
    CompletableFuture.runAsync(() -> clientRepository.save(clientMapper.tofromResponseToEntity(c)));
    return c;
  }

  private Function<Long, DeviceRequest> getLongDeviceRequestFunction() {
    return i -> {
      final var device = new DeviceRequest();
      device.setId(i);
      return device;
    };
  }

  private void calcCost(ClientResponse clientResponse) {
    Map<String, BigDecimal> countByServiceType = new HashMap<>();
    clientResponse.getServices().forEach(getServiceRequestConsumer(countByServiceType));
    Map<String, BigDecimal> quantityByDeviceType = new HashMap<>();
    clientResponse.getDevices().forEach(getDeviceRequestConsumer(quantityByDeviceType));
    final var totalCost =
        quantityByDeviceType.keySet().stream()
            .map(getStringBigDecimalFunction(countByServiceType, quantityByDeviceType))
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
    clientResponse.setCost(totalCost);
  }

  private Function<String, BigDecimal> getStringBigDecimalFunction(
      final Map<String, BigDecimal> countByServiceType,
      final Map<String, BigDecimal> quantityByDeviceType) {
    return type ->
        countByServiceType
            .getOrDefault(type, BigDecimal.ZERO)
            .multiply(quantityByDeviceType.getOrDefault(type, BigDecimal.ZERO));
  }

  private Consumer<DeviceRequest> getDeviceRequestConsumer(
      final Map<String, BigDecimal> quantityByDeviceType) {
    return device -> {
      quantityByDeviceType.put(
          device.getType(),
          quantityByDeviceType.getOrDefault(device.getType(), BigDecimal.ZERO).add(BigDecimal.ONE));
    };
  }

  private Consumer<ServiceRequest> getServiceRequestConsumer(
      final Map<String, BigDecimal> countByServiceType) {
    return service -> {
      countByServiceType.put(
          service.getType(),
          countByServiceType
              .getOrDefault(service.getType(), BigDecimal.ZERO)
              .add(service.getCost()));
    };
  }
}
