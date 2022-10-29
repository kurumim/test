package com.example.ninjaone.properties;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "types")
public class TypeProperties {

  public List<String> devices;

  public List<String> services;

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
