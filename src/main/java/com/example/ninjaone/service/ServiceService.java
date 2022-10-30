package com.example.ninjaone.service;

import com.example.ninjaone.controller.request.ServiceRequest;
import com.example.ninjaone.exceptions.ValidOperationException;
import com.example.ninjaone.model.ServiceEntity;
import com.example.ninjaone.properties.TypeProperties;
import com.example.ninjaone.repository.ServiceRepository;
import com.example.ninjaone.service.mappers.ServiceMapper;
import org.springframework.stereotype.Service;

@Service
public class ServiceService
    extends GenericService<
        ServiceRequest, ServiceRequest, ServiceEntity, ServiceRepository, ServiceMapper> {

  public static final String TYPE_IS_NOT_VALID = "%s type is not valid";

  public static final String NAME_IS_NOT_VALID = "%s name is not valid";
  private final TypeProperties typeProperties;

  private final ClientService clientService;

  public ServiceService(
      final ServiceRepository repository,
      final ServiceMapper mapper,
      final TypeProperties typeProperties,
      final ClientService clientService) {
    super(repository, mapper);
    this.typeProperties = typeProperties;
    this.clientService = clientService;
  }

  @Override
  public void validOperation(ServiceRequest serviceRequest) {
    if (!typeProperties.getDevices().contains(serviceRequest.getType()))
      throw new ValidOperationException(String.format(TYPE_IS_NOT_VALID, serviceRequest.getType()));
    if (!typeProperties.getServices().contains(serviceRequest.getName()))
      throw new ValidOperationException(String.format(NAME_IS_NOT_VALID, serviceRequest.getName()));
  }

  @Override
  public void updateCosts() {
    clientService.updateCosts();
  }
}
