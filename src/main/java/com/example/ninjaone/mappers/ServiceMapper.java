package com.example.ninjaone.mappers;

import com.example.ninjaone.model.ServiceEntity;
import com.example.ninjaone.request.ServiceRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceMapper
    extends GenericMapper<ServiceRequest, ServiceRequest, ServiceEntity> {}
