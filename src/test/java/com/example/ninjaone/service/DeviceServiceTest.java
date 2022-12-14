package com.example.ninjaone.service;

import static com.example.ninjaone.constants.TestConstants.INVALID;
import static com.example.ninjaone.constants.TestConstants.MAC;
import static com.example.ninjaone.constants.TestConstants.TYPE_IS_NOT_VALID;
import static com.example.ninjaone.constants.TestConstants.WINDOWS;
import static com.example.ninjaone.service.GenericService.NOT_FOUND;

import com.example.ninjaone.controller.request.DeviceRequest;
import com.example.ninjaone.exceptions.ValidOperationException;
import com.example.ninjaone.properties.TypeProperties;
import com.example.ninjaone.repository.DeviceRepository;
import com.example.ninjaone.service.mappers.DeviceMapperImpl;
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
public class DeviceServiceTest {

  public static final String WORKSTATION = "Workstation";
  @Mock private DeviceRepository repository;
  @Mock private DeviceMapperImpl deviceMapper;
  @Mock private TypeProperties typeProperties;

  @Mock private ClientService clientService;

  private DeviceService service;

  @Before
  public void setup() {
    Mockito.when(typeProperties.getDevices()).thenReturn(List.of(WINDOWS, MAC));
    this.service = new DeviceService(repository, deviceMapper, typeProperties, clientService);
  }

  @Test
  public void assertNotThrException() {
    final var deviceRequest = new DeviceRequest();
    deviceRequest.setType(MAC);
    deviceRequest.setName(WORKSTATION);
    Assertions.assertThatCode(() -> service.validOperation(deviceRequest))
        .doesNotThrowAnyException();
  }

  @Test
  public void assertUpdateThrExceptionWhenNotExist() {
    final var deviceRequest = new DeviceRequest();
    deviceRequest.setType(MAC);
    deviceRequest.setName(WORKSTATION);
    Mockito.when(repository.existsById(1L)).thenReturn(false);
    Assertions.assertThatCode(() -> service.updateEntity(deviceRequest, 1L))
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

  @Test
  public void assertThrExceptionWhenInvalidType() {
    final var request = new DeviceRequest();
    request.setType(INVALID);
    request.setName(WORKSTATION);
    Assertions.assertThatCode(() -> service.validOperation(request))
        .isInstanceOf(ValidOperationException.class)
        .extracting(throwable -> ((ValidOperationException) throwable).getMessages())
        .asList()
        .containsExactlyInAnyOrderElementsOf(
            List.of(String.format(TYPE_IS_NOT_VALID, request.getType())));
  }
}
