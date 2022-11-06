package com.example.ninjaone;

import com.example.ninjaone.model.ClientEntity;
import com.example.ninjaone.model.DeviceEntity;
import com.example.ninjaone.model.ServiceEntity;
import com.example.ninjaone.repository.ClientRepository;
import com.example.ninjaone.repository.DeviceRepository;
import com.example.ninjaone.repository.ServiceRepository;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    setupDevices();
    setupServices();
    setupClient();
  }

  private void setupDevices() {
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

  private void setupServices() {
    final List<ServiceEntity> services = new LinkedList<>();
    final var names = List.of("Antivirus", "Backup", "PSA", "Screen Share");
    final List<BigDecimal> costs =
        List.of(
            BigDecimal.valueOf(10),
            BigDecimal.valueOf(10),
            BigDecimal.valueOf(10),
            BigDecimal.valueOf(10));
    final var types = List.of("Windows", "Mac");
    final Map<String, BigDecimal> map =
        Map.of("Windows", BigDecimal.ONE, "Mac", BigDecimal.valueOf(1.25));
    IntStream.range(0, 4)
        .forEach(
            i -> {
              types.forEach(
                  t -> {
                    final var service = new ServiceEntity();
                    service.setName(names.get(i));
                    service.setCost(costs.get(i).multiply(map.get(t)));
                    service.setType(t);
                    services.add(service);
                  });
            });
    serviceRepository.saveAll(services);
  }

  private void setupClient() {
    IntStream.range(0, 10)
        .forEach(
            i -> {
              clientRepository.save(new ClientEntity());
            });
  }
}
