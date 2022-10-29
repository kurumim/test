package com.example.ninjaone.service;

import com.example.ninjaone.mappers.GenericMapper;
import com.example.ninjaone.model.GenericEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericService<REQUEST,RESPONSE,ENTITY extends GenericEntity,REPOSITORY extends CrudRepository<ENTITY,Long>,
        MAPPER extends GenericMapper<REQUEST,RESPONSE,ENTITY>> {

    public static final String NOT_FOUND = "Not Found";
    private final REPOSITORY repository;

    private final MAPPER mapper;

    public GenericService(final REPOSITORY repository, final MAPPER mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ENTITY getEntity(final Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException(NOT_FOUND));
    }

    public void deleteEntity(final Long id){
        repository.deleteById(id);
    }

    public RESPONSE addEntity(final REQUEST input){
        return mapper.toResponse(repository.save(mapper.toEntity(input)));
    }

    public RESPONSE updateEntity(final REQUEST input, final Long id){
        if(!repository.existsById(id))
            throw new RuntimeException(NOT_FOUND);
        final var entity = mapper.toEntity(input);
        entity.setId(id);
        return mapper.toResponse(repository.save(mapper.toEntity(input)));
    }

    public List<RESPONSE> getAll(){
        final List<ENTITY> responses = new ArrayList<>();
        repository.findAll().forEach(responses::add);
        return mapper.toResponse(responses);
    }
}
