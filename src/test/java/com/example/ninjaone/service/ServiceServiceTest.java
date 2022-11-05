package com.example.ninjaone.service;

import static com.example.ninjaone.constants.TestConstants.INVALID;
import static com.example.ninjaone.constants.TestConstants.MAC;
import static com.example.ninjaone.constants.TestConstants.NOT_FOUND;
import static com.example.ninjaone.constants.TestConstants.TYPE_IS_NOT_VALID;
import static com.example.ninjaone.constants.TestConstants.WINDOWS;

import com.example.ninjaone.controller.request.ServiceRequest;
import com.example.ninjaone.exceptions.ValidOperationException;
import com.example.ninjaone.properties.TypeProperties;
import com.example.ninjaone.repository.ServiceRepository;
import com.example.ninjaone.service.mappers.ServiceMapperImpl;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ServiceServiceTest {

  private static final String NAME_IS_NOT_VALID = "%s name is not valid";

  private static final String BACKUP = "Backup";
  @Mock private ServiceRepository repository;

  private final ServiceMapperImpl mapper = new ServiceMapperImpl();
  @Mock private TypeProperties typeProperties;

  @Mock private ClientService clientService;

  private ServiceService service;

  @Before
  public void setup() {
    Mockito.when(typeProperties.getServices())
        .thenReturn(List.of("Antivirus", BACKUP, "PSA", "Screen Share"));
    Mockito.when(typeProperties.getDevices()).thenReturn(List.of(WINDOWS, MAC));
    this.service = new ServiceService(repository, mapper, typeProperties, clientService);
  }

  @Test
  public void assertThrExceptionWhenInvalidTypeAndService() {
    final var serviceRequest = new ServiceRequest();
    serviceRequest.setType(INVALID);
    serviceRequest.setName(INVALID);
    Assertions.assertThatCode(() -> service.validOperation(serviceRequest))
        .isInstanceOf(ValidOperationException.class)
        .extracting(throwable -> ((ValidOperationException) throwable).getMessages())
        .asList()
        .containsExactlyInAnyOrderElementsOf(
            List.of(
                String.format(TYPE_IS_NOT_VALID, serviceRequest.getType()),
                String.format(NAME_IS_NOT_VALID, serviceRequest.getName())));
  }

  @Test
  public void assertThrExceptionWhenInvalidService() {
    final var serviceRequest = new ServiceRequest();
    serviceRequest.setType(WINDOWS);
    serviceRequest.setName(INVALID);
    Assertions.assertThatCode(() -> service.validOperation(serviceRequest))
        .isInstanceOf(ValidOperationException.class)
        .extracting(throwable -> ((ValidOperationException) throwable).getMessages())
        .asList()
        .containsExactlyInAnyOrderElementsOf(
            List.of(String.format(NAME_IS_NOT_VALID, serviceRequest.getName())));
  }

  @Test
  public void assertThrExceptionWhenInvalidType() {
    final var serviceRequest = new ServiceRequest();
    serviceRequest.setType(INVALID);
    serviceRequest.setName(BACKUP);
    Assertions.assertThatCode(() -> service.validOperation(serviceRequest))
        .isInstanceOf(ValidOperationException.class)
        .extracting(throwable -> ((ValidOperationException) throwable).getMessages())
        .asList()
        .containsExactlyInAnyOrderElementsOf(
            List.of(String.format(TYPE_IS_NOT_VALID, serviceRequest.getType())));
  }

  @Test
  public void assertNotThrException() {
    final var serviceRequest = new ServiceRequest();
    serviceRequest.setType(MAC);
    serviceRequest.setName(BACKUP);
    Assertions.assertThatCode(() -> service.validOperation(serviceRequest))
        .doesNotThrowAnyException();
  }

  @Test
  public void assertUpdateCostWhenUpdate() {
    final var serviceRequest = new ServiceRequest();
    serviceRequest.setType(MAC);
    serviceRequest.setName(BACKUP);
    serviceRequest.setCost(BigDecimal.TEN);
    Mockito.when(repository.existsById(1L)).thenReturn(true);
    service.updateEntity(serviceRequest, 1L);
    Mockito.verify(clientService, Mockito.times(1)).updateCosts();
  }

  @Test
  public void assertUpdateThrExceptionWhenNotExist() {
    final var serviceRequest = new ServiceRequest();
    serviceRequest.setType(MAC);
    serviceRequest.setName(BACKUP);
    serviceRequest.setCost(BigDecimal.TEN);
    Mockito.when(repository.existsById(1L)).thenReturn(false);
    Assertions.assertThatCode(() -> service.updateEntity(serviceRequest, 1L))
        .isInstanceOf(ValidOperationException.class)
        .extracting(throwable -> ((ValidOperationException) throwable).getMessages())
        .asList()
        .containsExactlyInAnyOrderElementsOf(List.of(String.format(NOT_FOUND, 1)));
  }

  @Test
  public void assertGetThrExceptionWhenNotExist() {
    Mockito.when(repository.findById(1L)).thenThrow(EntityNotFoundException.class);
    Assertions.assertThatCode(() -> service.getEntity(1L))
        .isInstanceOf(EntityNotFoundException.class);
  }
}
