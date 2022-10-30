package com.example.ninjaone.service;

import com.example.ninjaone.controller.request.ClientRequest;
import com.example.ninjaone.controller.request.DeviceRequest;
import com.example.ninjaone.controller.request.ServiceRequest;
import com.example.ninjaone.controller.response.ClientResponse;
import com.example.ninjaone.exceptions.ValidOperationException;
import com.example.ninjaone.model.ClientEntity;
import com.example.ninjaone.repository.ClientRepository;
import com.example.ninjaone.service.mappers.ClientMapper;
import java.util.List;
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

  public ClientResponse addServices(Long id, List<Long> serviceIds) {
    final var cliente =
        clientRepository
            .findById(id)
            .orElseThrow(() -> new ValidOperationException(String.format(NOT_FOUND, id)));
    final var clientResponse = clientMapper.toResponse(cliente);
    clientResponse.setServices(
        serviceIds.stream()
            .map(
                i -> {
                  final var service = new ServiceRequest();
                  service.setId(i);
                  return service;
                })
            .toList());
    return clientMapper.toResponse(
        clientRepository.save(clientMapper.tofromResponseToEntity(clientResponse)));
  }

  public ClientResponse addDevices(Long id, List<Long> deviceIds) {
    final var cliente =
        clientRepository
            .findById(id)
            .orElseThrow(() -> new ValidOperationException(String.format(NOT_FOUND, id)));
    final var clientResponse = clientMapper.toResponse(cliente);
    clientResponse.setDevices(
        deviceIds.stream()
            .map(
                i -> {
                  final var device = new DeviceRequest();
                  device.setId(i);
                  return device;
                })
            .toList());
    return clientMapper.toResponse(
        clientRepository.save(clientMapper.tofromResponseToEntity(clientResponse)));
  }

  public ClientResponse removeServices(Long id, List<Long> serviceIds) {
    final var cliente =
        clientRepository
            .findById(id)
            .orElseThrow(() -> new ValidOperationException(String.format(NOT_FOUND, id)));
    final var clientResponse = clientMapper.toResponse(cliente);
    clientResponse.setServices(
        clientResponse.getServices().stream()
            .filter(s -> !serviceIds.contains(s.getId()))
            .toList());
    return clientMapper.toResponse(
        clientRepository.save(clientMapper.tofromResponseToEntity(clientResponse)));
  }

  public ClientResponse removeDevices(Long id, List<Long> deviceIds) {
    final var cliente =
        clientRepository
            .findById(id)
            .orElseThrow(() -> new ValidOperationException(String.format(NOT_FOUND, id)));
    final var clientResponse = clientMapper.toResponse(cliente);
    clientResponse.setDevices(
        clientResponse.getDevices().stream().filter(s -> !deviceIds.contains(s.getId())).toList());
    return clientMapper.toResponse(
        clientRepository.save(clientMapper.tofromResponseToEntity(clientResponse)));
  }
}
