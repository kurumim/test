package com.example.ninjaone.model;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name = "Service")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "type"})})
public class ServiceEntity implements GenericEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private String name;

  @Column private BigDecimal cost;

  @Column private String type;

  @ManyToMany(mappedBy = "services")
  private List<ClientEntity> clients;

  public List<ClientEntity> getClients() {
    return clients;
  }

  public void setClients(final List<ClientEntity> clients) {
    this.clients = clients;
  }

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public BigDecimal getCost() {
    return cost;
  }

  public void setCost(final BigDecimal cost) {
    this.cost = cost;
  }
}
