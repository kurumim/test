package com.example.ninjaone.service.mappers;

import com.example.ninjaone.controller.request.ClientRequest;
import com.example.ninjaone.controller.response.ClientResponse;
import com.example.ninjaone.model.ClientEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper extends GenericMapper<ClientRequest, ClientResponse, ClientEntity> {}
