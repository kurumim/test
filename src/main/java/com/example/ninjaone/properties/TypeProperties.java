package com.example.ninjaone.properties;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "types")
public class TypeProperties {

  private List<String> devices;

  private List<String> services;

  private Map<String, BigDecimal> costs;

  public Map<String, BigDecimal> getCosts() {
    return costs;
  }

  public void setCosts(final Map<String, BigDecimal> costs) {
    this.costs = costs;
  }

  public List<String> getServices() {
    return services;
  }

  public void setServices(final List<String> services) {
    this.services = services;
  }

  public List<String> getDevices() {
    return devices;
  }

  public void setDevices(final List<String> devices) {
    this.devices = devices;
  }
}
