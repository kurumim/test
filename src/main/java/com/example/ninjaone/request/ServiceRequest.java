package com.example.ninjaone.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

public record ServiceRequest(Long id, @NotEmpty(message = "can't be empty") String name,
                             @NotNull(message = "can't be null") BigInteger cost, @NotEmpty(message = "can't be null") String type){}
