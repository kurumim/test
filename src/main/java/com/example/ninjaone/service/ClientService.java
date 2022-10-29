package com.example.ninjaone.service;

import com.example.ninjaone.mappers.ClientMapper;
import com.example.ninjaone.model.ClientEntity;
import com.example.ninjaone.repository.ClientRepository;
import com.example.ninjaone.request.ClientRequest;
import org.springframework.stereotype.Service;

@Service
public class ClientService extends GenericService<ClientRequest, ClientRequest, ClientEntity, ClientRepository, ClientMapper> {

    public ClientService(final ClientRepository repository, final ClientMapper mapper) {
        super(repository, mapper);
    }
}
