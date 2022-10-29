package com.example.ninjaone.service.mappers;

import com.example.ninjaone.controller.request.ServiceRequest;
import com.example.ninjaone.model.ServiceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceMapper
    extends GenericMapper<ServiceRequest, ServiceRequest, ServiceEntity> {}
