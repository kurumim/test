package com.example.ninjaone.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity(name = "Client")
public class ClientEntity implements GenericEntity {

    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal cost;

    @ManyToMany
    private List<DeviceEntity> deviceEntities;

    @ManyToMany
    private List<ServiceEntity> serviceEntities;

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

    public List<DeviceEntity> getDeviceEntities() {
        return deviceEntities;
    }

    public void setDeviceEntities(final List<DeviceEntity> deviceEntities) {
        this.deviceEntities = deviceEntities;
    }

    public List<ServiceEntity> getServiceEntities() {
        return serviceEntities;
    }

    public void setServiceEntities(final List<ServiceEntity> serviceEntities) {
        this.serviceEntities = serviceEntities;
    }
}
