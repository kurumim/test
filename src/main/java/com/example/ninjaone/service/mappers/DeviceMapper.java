package com.example.ninjaone.service.mappers;

import com.example.ninjaone.controller.request.DeviceRequest;
import com.example.ninjaone.model.DeviceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceMapper extends GenericMapper<DeviceRequest, DeviceRequest, DeviceEntity> {}
