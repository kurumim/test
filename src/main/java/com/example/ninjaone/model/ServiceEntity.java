package com.example.ninjaone.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;

@Entity(name="Service")
public class ServiceEntity implements GenericEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true)
    private String name;

    private BigDecimal cost;

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
