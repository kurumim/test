package com.example.ninjaone;

import com.example.ninjaone.model.DeviceEntity;
import com.example.ninjaone.repository.ClientRepository;
import com.example.ninjaone.repository.DeviceRepository;
import com.example.ninjaone.repository.ServiceRepository;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataComponent {

  private final DeviceRepository deviceRepository;

  private final ServiceRepository serviceRepository;

  private final ClientRepository clientRepository;

  public DataComponent(
      final DeviceRepository deviceRepository,
      final ServiceRepository serviceRepository,
      final ClientRepository clientRepository) {
    this.deviceRepository = deviceRepository;
    this.serviceRepository = serviceRepository;
    this.clientRepository = clientRepository;
  }

  @PostConstruct
  public void setupData() {
    final List<DeviceEntity> devices = new LinkedList<>();
    final var names = List.of("Workstation", "PC", "Server", "MacBook", "Notebook");
    final var types = List.of("Windows", "Windows", "Windows", "Mac", "Mac");
    IntStream.range(0, 5)
        .forEach(
            i -> {
              final var device = new DeviceEntity();
              device.setName(names.get(i));
              device.setType(types.get(i));
              devices.add(device);
            });
    deviceRepository.saveAll(devices);
  }
}
