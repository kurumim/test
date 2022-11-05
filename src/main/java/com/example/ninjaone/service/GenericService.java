package com.example.ninjaone.service;

import com.example.ninjaone.exceptions.ValidOperationException;
import com.example.ninjaone.model.GenericEntity;
import com.example.ninjaone.service.mappers.GenericMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public abstract class GenericService<
    REQUEST,
    RESPONSE,
    ENTITY extends GenericEntity,
    REPOSITORY extends CrudRepository<ENTITY, Long>,
    MAPPER extends GenericMapper<REQUEST, RESPONSE, ENTITY>> {

  public static final String NOT_FOUND = "Entity with id %s was not found";
  private final REPOSITORY repository;

  private final MAPPER mapper;

  public GenericService(final REPOSITORY repository, final MAPPER mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public RESPONSE getEntity(final Long id) {
    return repository
        .findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new ValidOperationException(String.format(NOT_FOUND, id)));
  }

  public void deleteEntity(final Long id) {
    repository.deleteById(id);
  }

  public RESPONSE addEntity(final REQUEST input) {
    validOperation(input);
    return mapper.toResponse(repository.save(mapper.toEntity(input)));
  }

  public RESPONSE updateEntity(final REQUEST input, final Long id) {
    validOperation(input);
    if (!repository.existsById(id)) throw new ValidOperationException(String.format(NOT_FOUND, id));
    final var entity = mapper.toEntity(input);
    entity.setId(id);
    final var saved = repository.save(entity);
    final var response = mapper.toResponse(saved);
    updateCosts();
    return response;
  }

  public List<RESPONSE> getAll() {
    final List<ENTITY> responses = new ArrayList<>();
    repository.findAll().forEach(responses::add);
    return mapper.toResponse(responses);
  }

  public void validOperation(REQUEST request) {
    throw new UnsupportedOperationException();
  }

  public void updateCosts() {
    throw new UnsupportedOperationException();
  }
}
