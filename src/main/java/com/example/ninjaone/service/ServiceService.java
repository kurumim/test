package com.example.ninjaone.service;

import com.example.ninjaone.mappers.ServiceMapper;
import com.example.ninjaone.model.ServiceEntity;
import com.example.ninjaone.properties.TypeProperties;
import com.example.ninjaone.repository.ServiceRepository;
import com.example.ninjaone.request.ServiceRequest;
import org.springframework.stereotype.Service;

@Service
public class ServiceService
    extends GenericService<
        ServiceRequest, ServiceRequest, ServiceEntity, ServiceRepository, ServiceMapper> {

  public static final String TYPE_IS_NOT_VALID = "%s is not valid";
  private final TypeProperties typeProperties;

  public ServiceService(
      final ServiceRepository repository,
      final ServiceMapper mapper,
      final TypeProperties typeProperties) {
    super(repository, mapper);
    this.typeProperties = typeProperties;
  }

  @Override
  public void validType(ServiceRequest serviceRequest) {
    if (!typeProperties.getTypes().contains(serviceRequest.type()))
      throw new RuntimeException(String.format(TYPE_IS_NOT_VALID, serviceRequest.type()));
  }
}
