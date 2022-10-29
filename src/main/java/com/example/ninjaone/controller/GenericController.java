package com.example.ninjaone.controller;

import com.example.ninjaone.model.GenericEntity;
import com.example.ninjaone.service.GenericService;
import com.example.ninjaone.service.mappers.GenericMapper;
import java.util.List;
import javax.validation.Valid;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class GenericController<
    REQUEST,
    RESPONSE,
    ENTITY extends GenericEntity,
    REPOSITORY extends CrudRepository<ENTITY, Long>,
    MAPPER extends GenericMapper<REQUEST, RESPONSE, ENTITY>,
    SERVICE extends GenericService<REQUEST, RESPONSE, ENTITY, REPOSITORY, MAPPER>> {

  private final SERVICE service;

  public GenericController(final SERVICE service) {
    this.service = service;
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public RESPONSE addEntity(@Valid @RequestBody REQUEST request) {
    return service.addEntity(request);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void deleteEntity(@PathVariable("id") Long id) {
    service.deleteEntity(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(code = HttpStatus.OK)
  public RESPONSE updateEntity(@RequestBody @Valid REQUEST request, @PathVariable("id") Long id) {
    return service.updateEntity(request, id);
  }

  @GetMapping("/{id}")
  @ResponseStatus(code = HttpStatus.OK)
  public RESPONSE getByIdEntity(@PathVariable("id") Long id) {
    return service.getEntity(id);
  }

  @GetMapping
  @ResponseStatus(code = HttpStatus.OK)
  public List<RESPONSE> getAllEntity() {
    return service.getAll();
  }
}
