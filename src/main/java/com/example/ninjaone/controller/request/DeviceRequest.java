package com.example.ninjaone.controller.request;

import javax.validation.constraints.NotEmpty;

public final class DeviceRequest {
  private Long id;
  private @NotEmpty(message = "can't be empty") String name;
  private @NotEmpty(message = "can't be empty") String type;

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
