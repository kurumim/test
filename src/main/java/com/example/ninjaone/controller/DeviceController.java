package com.example.ninjaone.controller;

import com.example.ninjaone.controller.request.DeviceRequest;
import com.example.ninjaone.model.DeviceEntity;
import com.example.ninjaone.repository.DeviceRepository;
import com.example.ninjaone.service.DeviceService;
import com.example.ninjaone.service.mappers.DeviceMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devices")
public class DeviceController
    extends GenericController<
        DeviceRequest, DeviceRequest, DeviceEntity, DeviceRepository, DeviceMapper, DeviceService> {

  private final DeviceService deviceService;

  public DeviceController(final DeviceService service) {
    super(service);
    deviceService = service;
  }

  @GetMapping("/update-prices")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updatePrices() {
    deviceService.updateCosts();
  }
}
