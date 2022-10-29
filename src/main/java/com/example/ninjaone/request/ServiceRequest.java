package com.example.ninjaone.request;

import java.math.BigInteger;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record ServiceRequest(
    Long id,
    @NotEmpty(message = "can't be empty") String name,
    @NotNull(message = "can't be null") BigInteger cost,
    @NotEmpty(message = "can't be null") String type) {}
