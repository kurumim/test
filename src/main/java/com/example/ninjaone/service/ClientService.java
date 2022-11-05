package com.example.ninjaone.service;

import static com.example.ninjaone.CostHelper.calcCost;

import com.example.ninjaone.CostHelper;
import com.example.ninjaone.controller.request.ClientRequest;
import com.example.ninjaone.controller.request.DeviceRequest;
import com.example.ninjaone.controller.request.ServiceRequest;
import com.example.ninjaone.controller.response.ClientResponse;
import com.example.ninjaone.exceptions.ValidOperationException;
import com.example.ninjaone.model.ClientEntity;
import com.example.ninjaone.properties.TypeProperties;
import com.example.ninjaone.repository.ClientRepository;
import com.example.ninjaone.service.mappers.ClientMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ClientService
    extends GenericService<
        ClientRequest, ClientResponse, ClientEntity, ClientRepository, ClientMapper> {
  private final ClientRepository clientRepository;

  private final ClientMapper clientMapper;

  private final TypeProperties typeProperties;

  public ClientService(
      final ClientRepository repository,
      final ClientMapper mapper,
      final TypeProperties typeProperties) {
    super(repository, mapper);
    this.clientRepository = repository;
    this.clientMapper = mapper;
    this.typeProperties = typeProperties;
  }

  @Override
  public void validOperation(ClientRequest clientRequest) {}

  public ClientResponse setServices(Long id, List<Long> serviceIds) {
    final var client =
        clientRepository
            .findById(id)
            .orElseThrow(() -> new ValidOperationException(String.format(NOT_FOUND, id)));
    final var clientResponse = clientMapper.toResponse(client);
    clientResponse.setServices(serviceIds.stream().map(getLongServiceRequestFunction()).toList());
    final var c =
        clientMapper.toResponse(
            clientRepository.save(clientMapper.tofromResponseToEntity(clientResponse)));
    calcCost(c, typeProperties);
    clientRepository.save(clientMapper.tofromResponseToEntity(c));
    return c;
  }

  private Function<Long, ServiceRequest> getLongServiceRequestFunction() {
    return i -> {
      final var service = new ServiceRequest();
      service.setId(i);
      return service;
    };
  }

  public ClientResponse setDevices(Long id, List<Long> deviceIds) {
    final var client =
        clientRepository
            .findById(id)
            .orElseThrow(() -> new ValidOperationException(String.format(NOT_FOUND, id)));
    final var clientResponse = clientMapper.toResponse(client);
    clientResponse.setDevices(deviceIds.stream().map(getLongDeviceRequestFunction()).toList());
    final var c =
        clientMapper.toResponse(
            clientRepository.save(clientMapper.tofromResponseToEntity(clientResponse)));
    calcCost(c, typeProperties);
    clientRepository.save(clientMapper.tofromResponseToEntity(c));
    return c;
  }

  private Function<Long, DeviceRequest> getLongDeviceRequestFunction() {
    return i -> {
      final var device = new DeviceRequest();
      device.setId(i);
      return device;
    };
  }

  @Override
  public void updateCosts() {
    final var clients = new ArrayList<>();
    final var tmp = clientRepository.findAll();
    tmp.forEach(clients::add);
    if (!CollectionUtils.isEmpty(clients)) {
      clientRepository.saveAll(
          clients.stream()
              .map(
                  c ->
                      CostHelper.calcCost(
                          clientMapper.toResponse((ClientEntity) c), typeProperties))
              .map(clientMapper::tofromResponseToEntity)
              .toList());
    }
  }
}
