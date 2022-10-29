package com.example.ninjaone.service;

import com.example.ninjaone.mappers.DeviceMapper;
import com.example.ninjaone.model.DeviceEntity;
import com.example.ninjaone.repository.DeviceRepository;
import com.example.ninjaone.request.DeviceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService extends GenericService<DeviceRequest,DeviceRequest,DeviceEntity,DeviceRepository,DeviceMapper> {

    @Autowired
    public DeviceService(final DeviceRepository deviceRepository, final DeviceMapper deviceMapper) {
        super(deviceRepository,deviceMapper);
    }

}