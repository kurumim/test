package com.example.ninjaone.controller;

import com.example.ninjaone.controller.request.DeviceRequest;
import com.example.ninjaone.model.DeviceEntity;
import com.example.ninjaone.repository.DeviceRepository;
import com.example.ninjaone.service.DeviceService;
import com.example.ninjaone.service.mappers.DeviceMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devices")
public class DeviceController
    extends GenericController<
        DeviceRequest, DeviceRequest, DeviceEntity, DeviceRepository, DeviceMapper, DeviceService> {

  public DeviceController(final DeviceService service) {
    super(service);
  }
}
