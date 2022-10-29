package com.example.ninjaone.mappers;

import com.example.ninjaone.model.ClientEntity;
import com.example.ninjaone.request.ClientRequest;
import com.example.ninjaone.response.ClientResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper extends GenericMapper<ClientRequest, ClientResponse, ClientEntity> {}
