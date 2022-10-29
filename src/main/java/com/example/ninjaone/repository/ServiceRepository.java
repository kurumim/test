package com.example.ninjaone.repository;

import com.example.ninjaone.model.ServiceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends CrudRepository<ServiceEntity,Long> {
}
