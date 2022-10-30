package com.example.ninjaone.controller.request;

import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public final class ServiceRequest {
  private Long id;
  private @NotEmpty(message = "can't be empty") String name;
  private @NotNull(message = "can't be null") BigDecimal cost;
  private @NotEmpty(message = "can't be null") String type;

  public Long getId() {
    return id;
  }

  public BigDecimal getCost() {
    return cost;
  }

  public void setCost(final BigDecimal cost) {
    this.cost = cost;
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
