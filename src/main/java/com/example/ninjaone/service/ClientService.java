package com.example.ninjaone.service;

import com.example.ninjaone.controller.request.ClientRequest;
import com.example.ninjaone.controller.response.ClientResponse;
import com.example.ninjaone.model.ClientEntity;
import com.example.ninjaone.repository.ClientRepository;
import com.example.ninjaone.service.mappers.ClientMapper;
import org.springframework.stereotype.Service;

@Service
public class ClientService
    extends GenericService<
        ClientRequest, ClientResponse, ClientEntity, ClientRepository, ClientMapper> {

  public ClientService(final ClientRepository repository, final ClientMapper mapper) {
    super(repository, mapper);
  }

  @Override
  public void validOperation(ClientRequest clientRequest) {
    return;
  }
}
