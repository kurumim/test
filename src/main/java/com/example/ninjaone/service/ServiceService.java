package com.example.ninjaone.service;

import com.example.ninjaone.mappers.ServiceMapper;
import com.example.ninjaone.model.ServiceEntity;
import com.example.ninjaone.repository.ServiceRepository;
import com.example.ninjaone.request.ServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceService extends GenericService<ServiceRequest,ServiceRequest, ServiceEntity, ServiceRepository, ServiceMapper> {

    @Autowired
    public ServiceService(final ServiceRepository repository, final ServiceMapper mapper) {
        super(repository, mapper);
    }
}
