package com.example.ninjaone.controller.response;

import com.example.ninjaone.controller.request.DeviceRequest;
import com.example.ninjaone.controller.request.ServiceRequest;
import java.math.BigDecimal;
import java.util.List;

public record ClientResponse(
    Long id, BigDecimal cost, List<ServiceRequest> services, List<DeviceRequest> devices) {}
