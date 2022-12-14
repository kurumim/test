package com.example.ninjaone.controller;

import com.example.ninjaone.controller.request.ServiceRequest;
import com.example.ninjaone.model.ServiceEntity;
import com.example.ninjaone.repository.ServiceRepository;
import com.example.ninjaone.service.ServiceService;
import com.example.ninjaone.service.mappers.ServiceMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
public class ServiceController
    extends GenericController<
        ServiceRequest,
        ServiceRequest,
        ServiceEntity,
        ServiceRepository,
        ServiceMapper,
        ServiceService> {

  public ServiceController(final ServiceService service) {
    super(service);
  }
}
