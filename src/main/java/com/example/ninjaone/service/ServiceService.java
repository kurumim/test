package com.example.ninjaone.service;

import com.example.ninjaone.CostHelper;
import com.example.ninjaone.controller.request.ServiceRequest;
import com.example.ninjaone.exceptions.ValidOperationException;
import com.example.ninjaone.model.ServiceEntity;
import com.example.ninjaone.properties.TypeProperties;
import com.example.ninjaone.repository.ClientRepository;
import com.example.ninjaone.repository.ServiceRepository;
import com.example.ninjaone.service.mappers.ClientMapper;
import com.example.ninjaone.service.mappers.ServiceMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ServiceService
    extends GenericService<
        ServiceRequest, ServiceRequest, ServiceEntity, ServiceRepository, ServiceMapper> {

  public static final String TYPE_IS_NOT_VALID = "%s type is not valid";

  public static final String NAME_IS_NOT_VALID = "%s name is not valid";
  private final TypeProperties typeProperties;

  private final ClientMapper clientMapper;

  private final ClientRepository clientRepository;

  public ServiceService(
      final ServiceRepository repository,
      final ServiceMapper mapper,
      final TypeProperties typeProperties,
      final ClientMapper clientMapper,
      final ClientRepository clientRepository) {
    super(repository, mapper);
    this.typeProperties = typeProperties;
    this.clientMapper = clientMapper;
    this.clientRepository = clientRepository;
  }

  @Override
  public void validOperation(ServiceRequest serviceRequest) {
    if (!typeProperties.getDevices().contains(serviceRequest.getType()))
      throw new ValidOperationException(String.format(TYPE_IS_NOT_VALID, serviceRequest.getType()));
    if (!typeProperties.getServices().contains(serviceRequest.getName()))
      throw new ValidOperationException(String.format(NAME_IS_NOT_VALID, serviceRequest.getName()));
  }

  @Override
  public void updateCosts(ServiceEntity entity) {
    final var clients = entity.getClients();
    if (!CollectionUtils.isEmpty(clients)) {
      clientRepository.saveAll(
          clients.stream()
              .map(c -> CostHelper.calcCost(clientMapper.toResponse(c), typeProperties))
              .map(clientMapper::tofromResponseToEntity)
              .toList());
    }
  }
}
