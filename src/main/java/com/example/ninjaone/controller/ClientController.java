package com.example.ninjaone.controller;

import com.example.ninjaone.controller.request.ClientRequest;
import com.example.ninjaone.controller.response.ClientResponse;
import com.example.ninjaone.model.ClientEntity;
import com.example.ninjaone.repository.ClientRepository;
import com.example.ninjaone.service.ClientService;
import com.example.ninjaone.service.mappers.ClientMapper;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.MethodNotAllowedException;

@RestController
@RequestMapping("/clients")
public class ClientController
    extends GenericController<
        ClientRequest,
        ClientResponse,
        ClientEntity,
        ClientRepository,
        ClientMapper,
        ClientService> {

  private final ClientService clientService;

  public ClientController(final ClientService service) {
    super(service);
    this.clientService = service;
  }

  @Override
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public ClientResponse addEntity(@Valid @RequestBody(required = false) ClientRequest request) {
    return clientService.addEntity(new ClientRequest());
  }

  @PostMapping("/{id}/add-services")
  @ResponseStatus(code = HttpStatus.CREATED)
  public ClientResponse addService(
      @PathVariable("id") Long id, @RequestBody List<Long> serviceIds) {
    return clientService.addServices(id, serviceIds);
  }

  @PostMapping("/{id}/remove-services")
  @ResponseStatus(code = HttpStatus.CREATED)
  public ClientResponse removeService(
      @PathVariable("id") Long id, @RequestBody List<Long> serviceIds) {
    return clientService.removeServices(id, serviceIds);
  }

  @PostMapping("/{id}/add-devices")
  @ResponseStatus(code = HttpStatus.CREATED)
  public ClientResponse addDevice(@PathVariable("id") Long id, @RequestBody List<Long> deviceIds) {
    return clientService.addDevices(id, deviceIds);
  }

  @PostMapping("/{id}/remove-devices")
  @ResponseStatus(code = HttpStatus.CREATED)
  public ClientResponse removeDevice(
      @PathVariable("id") Long id, @RequestBody List<Long> deviceIds) {
    return clientService.removeDevices(id, deviceIds);
  }

  @Override
  @PutMapping("/{id}")
  @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
  public ClientResponse updateEntity(
      @RequestBody(required = false) @Valid ClientRequest request, @PathVariable("id") Long id) {
    throw new MethodNotAllowedException(
        "Method Not Allowed", List.of(HttpMethod.DELETE, HttpMethod.GET));
  }
}
