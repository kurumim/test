package com.example.ninjaone.repository;

import com.example.ninjaone.model.DeviceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends CrudRepository<DeviceEntity, Long> {}
