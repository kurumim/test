package com.example.ninjaone.service;

import com.example.ninjaone.mappers.DeviceMapper;
import com.example.ninjaone.model.DeviceEntity;
import com.example.ninjaone.properties.TypeProperties;
import com.example.ninjaone.repository.DeviceRepository;
import com.example.ninjaone.request.DeviceRequest;
import org.springframework.stereotype.Service;

@Service
public class DeviceService extends GenericService<DeviceRequest,DeviceRequest,DeviceEntity,DeviceRepository,DeviceMapper> {


    public static final String TYPE_IS_NOT_VALID = "%s is not valid";
    private final TypeProperties typeProperties;

    public DeviceService(final DeviceRepository deviceRepository, final DeviceMapper deviceMapper, final TypeProperties typeProperties) {
        super(deviceRepository,deviceMapper);
        this.typeProperties = typeProperties;
    }

    @Override
    public void validType(DeviceRequest deviceRequest){
        if(!typeProperties.getTypes().contains(deviceRequest.type()))
            throw new RuntimeException(String.format(TYPE_IS_NOT_VALID,deviceRequest.type()));
    }

}