package com.example.ninjaone.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name = "Device")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "type"})})
public class DeviceEntity implements GenericEntity {

  @Id @GeneratedValue private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String type;

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

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }
}
