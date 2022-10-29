package com.example.ninjaone.controller;

import com.example.ninjaone.controller.request.ClientRequest;
import com.example.ninjaone.controller.response.ClientResponse;
import com.example.ninjaone.model.ClientEntity;
import com.example.ninjaone.repository.ClientRepository;
import com.example.ninjaone.service.ClientService;
import com.example.ninjaone.service.mappers.ClientMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  public ClientController(final ClientService service) {
    super(service);
  }
}
