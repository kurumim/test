package com.example.ninjaone.model;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity(name = "Client")
public class ClientEntity implements GenericEntity {

  @Id @GeneratedValue private Long id;

  private BigDecimal cost;

  @ManyToMany private List<DeviceEntity> devices;

  @ManyToMany private List<ServiceEntity> services;

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

  public List<DeviceEntity> getDevices() {
    return devices;
  }

  public void setDevices(final List<DeviceEntity> devices) {
    this.devices = devices;
  }

  public List<ServiceEntity> getServices() {
    return services;
  }

  public void setServices(final List<ServiceEntity> services) {
    this.services = services;
  }
}
