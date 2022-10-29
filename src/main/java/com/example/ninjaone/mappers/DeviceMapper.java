package com.example.ninjaone.mappers;

import com.example.ninjaone.model.DeviceEntity;
import com.example.ninjaone.request.DeviceRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceMapper extends GenericMapper<DeviceRequest,DeviceRequest,DeviceEntity> {

}
