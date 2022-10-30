package com.example.ninjaone.service;

import com.example.ninjaone.controller.request.DeviceRequest;
import com.example.ninjaone.exceptions.ValidOperationException;
import com.example.ninjaone.model.DeviceEntity;
import com.example.ninjaone.properties.TypeProperties;
import com.example.ninjaone.repository.DeviceRepository;
import com.example.ninjaone.service.mappers.DeviceMapper;
import org.springframework.stereotype.Service;

@Service
public class DeviceService
    extends GenericService<
        DeviceRequest, DeviceRequest, DeviceEntity, DeviceRepository, DeviceMapper> {

  public static final String TYPE_IS_NOT_VALID = "%s is not valid";
  private final TypeProperties typeProperties;

  private final ClientService clientService;

  public DeviceService(
      final DeviceRepository deviceRepository,
      final DeviceMapper deviceMapper,
      final TypeProperties typeProperties,
      final ClientService clientService) {
    super(deviceRepository, deviceMapper);
    this.typeProperties = typeProperties;
    this.clientService = clientService;
  }

  @Override
  public void validOperation(DeviceRequest deviceRequest) {
    if (!typeProperties.getDevices().contains(deviceRequest.getType()))
      throw new ValidOperationException(String.format(TYPE_IS_NOT_VALID, deviceRequest.getType()));
  }

  @Override
  public void updateCosts() {
    clientService.updateCosts();
  }
}
