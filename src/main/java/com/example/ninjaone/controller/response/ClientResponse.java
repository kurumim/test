package com.example.ninjaone.controller.response;

import com.example.ninjaone.controller.request.DeviceRequest;
import com.example.ninjaone.controller.request.ServiceRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class ClientResponse {
  private Long id;
  private BigDecimal cost;
  private List<ServiceRequest> services = new ArrayList<>();
  private List<DeviceRequest> devices = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public BigDecimal getCost() {
    return cost;
  }

  public void setCost(final BigDecimal cost) {
    this.cost = cost;
  }

  public List<ServiceRequest> getServices() {
    return services;
  }

  public void setServices(final List<ServiceRequest> services) {
    this.services = services;
  }

  public List<DeviceRequest> getDevices() {
    return devices;
  }

  public void setDevices(final List<DeviceRequest> devices) {
    this.devices = devices;
  }
}
