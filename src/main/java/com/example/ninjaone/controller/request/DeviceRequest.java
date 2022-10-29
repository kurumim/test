package com.example.ninjaone.controller.request;

import javax.validation.constraints.NotEmpty;

public record DeviceRequest(
    Long id,
    @NotEmpty(message = "can't be empty") String name,
    @NotEmpty(message = "can't be empty") String type) {}