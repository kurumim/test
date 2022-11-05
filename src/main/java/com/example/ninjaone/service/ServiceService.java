package com.example.ninjaone.service;

import static com.example.ninjaone.constants.ServiceConstants.TYPE_IS_NOT_VALID;

import com.example.ninjaone.controller.request.ServiceRequest;
import com.example.ninjaone.exceptions.ValidOperationException;
import com.example.ninjaone.model.ServiceEntity;
import com.example.ninjaone.properties.TypeProperties;
import com.example.ninjaone.repository.ServiceRepository;
import com.example.ninjaone.service.mappers.ServiceMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ServiceService
    extends GenericService<
        ServiceRequest, ServiceRequest, ServiceEntity, ServiceRepository, ServiceMapper> {

  private static final String NAME_IS_NOT_VALID = "%s name is not valid";
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
    final List<String> errors = new ArrayList<>();
    if (!typeProperties.getDevices().contains(serviceRequest.getType()))
      errors.add(String.format(TYPE_IS_NOT_VALID, serviceRequest.getType()));
    if (!typeProperties.getServices().contains(serviceRequest.getName()))
      errors.add(String.format(NAME_IS_NOT_VALID, serviceRequest.getName()));
    if (!errors.isEmpty()) throw new ValidOperationException(errors);
  }

  @Override
  public void updateCosts() {
    clientService.updateCosts();
  }
}
