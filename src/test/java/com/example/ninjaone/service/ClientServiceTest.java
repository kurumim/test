package com.example.ninjaone.service;

import com.example.ninjaone.properties.TypeProperties;
import com.example.ninjaone.repository.ClientRepository;
import com.example.ninjaone.service.mappers.ClientMapperImpl;
import javax.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {

  @Mock private ClientRepository repository;

  @Mock private ClientMapperImpl mapper;

  @Mock private TypeProperties typeProperties;

  private ClientService service;

  @Before
  public void setup() {
    this.service = new ClientService(repository, mapper, typeProperties);
  }

  @Test
  public void assertGetThrExceptionWhenNotExist() {
    Mockito.when(repository.findById(1L)).thenThrow(EntityNotFoundException.class);
    Assertions.assertThatCode(() -> service.getEntity(1L))
        .isInstanceOf(EntityNotFoundException.class);
  }
}
