package com.example.ninjaone;

import com.example.ninjaone.controller.request.DeviceRequest;
import com.example.ninjaone.controller.request.ServiceRequest;
import com.example.ninjaone.controller.response.ClientResponse;
import com.example.ninjaone.properties.TypeProperties;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class CostHelper {

  private CostHelper() {}

  public static ClientResponse calcCost(
      ClientResponse clientResponse, TypeProperties typeProperties) {
    Map<String, BigDecimal> countByServiceType = new HashMap<>();
    clientResponse.getServices().forEach(getServiceRequestConsumer(countByServiceType));
    Map<String, BigDecimal> quantityByDeviceType = new HashMap<>();
    clientResponse.getDevices().forEach(getDeviceRequestConsumer(quantityByDeviceType));
    final var totalCost =
        quantityByDeviceType.keySet().stream()
            .map(
                getCostByServices(
                    countByServiceType, quantityByDeviceType, typeProperties.getCosts()))
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
    clientResponse.setCost(totalCost);
    return clientResponse;
  }

  private static Consumer<ServiceRequest> getServiceRequestConsumer(
      final Map<String, BigDecimal> countByServiceType) {
    return service -> {
      countByServiceType.put(
          service.getType(),
          countByServiceType
              .getOrDefault(service.getType(), BigDecimal.ZERO)
              .add(service.getCost()));
    };
  }

  private static Consumer<DeviceRequest> getDeviceRequestConsumer(
      final Map<String, BigDecimal> quantityByDeviceType) {
    return device -> {
      quantityByDeviceType.put(
          device.getType(),
          quantityByDeviceType.getOrDefault(device.getType(), BigDecimal.ZERO).add(BigDecimal.ONE));
    };
  }

  private static Function<String, BigDecimal> getCostByServices(
      final Map<String, BigDecimal> countByServiceType,
      final Map<String, BigDecimal> quantityByDeviceType,
      Map<String, BigDecimal> defaultValuePerDevice) {
    return type ->
        countByServiceType
            .getOrDefault(type, BigDecimal.ZERO)
            .multiply(quantityByDeviceType.getOrDefault(type, BigDecimal.ZERO))
            .add(
                defaultValuePerDevice
                    .getOrDefault(type, BigDecimal.ZERO)
                    .multiply(quantityByDeviceType.getOrDefault(type, BigDecimal.ZERO)));
  }
}
