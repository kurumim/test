package com.example.ninjaone.service;

import com.example.ninjaone.mappers.GenericMapper;
import com.example.ninjaone.model.GenericEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericService<REQUEST,RESPONSE,ENTITY extends GenericEntity,REPOSITORY extends CrudRepository<ENTITY,Long>,
        MAPPER extends GenericMapper<REQUEST,RESPONSE,ENTITY>> {

    public static final String NOT_FOUND = "Entity with id %s was not Found";
    private final REPOSITORY repository;

    private final MAPPER mapper;

    public GenericService(final REPOSITORY repository, final MAPPER mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public RESPONSE getEntity(final Long id){
        return repository.findById(id).map(mapper::toResponse).orElseThrow(() ->
                new RuntimeException(String.format(NOT_FOUND,id)));
    }

    public void deleteEntity(final Long id){
        repository.deleteById(id);
    }

    public RESPONSE addEntity(final REQUEST input){
        validType(input);
        return mapper.toResponse(repository.save(mapper.toEntity(input)));
    }

    public RESPONSE updateEntity(final REQUEST input, final Long id){
        if(!repository.existsById(id))
            throw new RuntimeException(NOT_FOUND);
        validType(input);
        final var entity = mapper.toEntity(input);
        entity.setId(id);
        return mapper.toResponse(repository.save(entity));
    }

    public List<RESPONSE> getAll(){
        final List<ENTITY> responses = new ArrayList<>();
        repository.findAll().forEach(responses::add);
        return mapper.toResponse(responses);
    }

    public void validType(REQUEST request) {
        throw new UnsupportedOperationException();
    }
}
