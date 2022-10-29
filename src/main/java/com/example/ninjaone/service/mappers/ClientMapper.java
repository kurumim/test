package com.example.ninjaone.service.mappers;

import com.example.ninjaone.controller.request.ClientRequest;
import com.example.ninjaone.controller.request.ServiceRequest;
import com.example.ninjaone.controller.response.ClientResponse;
import com.example.ninjaone.model.ClientEntity;
import com.example.ninjaone.model.ServiceEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper extends GenericMapper<ClientRequest, ClientResponse, ClientEntity> {

  ServiceRequest toServiceRequest(ServiceEntity serviceEntity);

  ServiceEntity toServiceEntity(ServiceRequest serviceRequest);

  List<ServiceRequest> toServiceRequests(List<ServiceEntity> serviceEntities);

  List<ServiceEntity> toServiceEntities(List<ServiceRequest> serviceRequests);
}
