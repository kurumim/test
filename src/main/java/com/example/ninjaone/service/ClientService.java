package com.example.ninjaone.service;

import com.example.ninjaone.mappers.ClientMapper;
import com.example.ninjaone.model.ClientEntity;
import com.example.ninjaone.repository.ClientRepository;
import com.example.ninjaone.request.ClientRequest;
import com.example.ninjaone.response.ClientResponse;
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
