package com.example.ninjaone.response;

import com.example.ninjaone.request.DeviceRequest;
import com.example.ninjaone.request.ServiceRequest;
import java.math.BigDecimal;
import java.util.List;

public record ClientResponse(
    Long id, BigDecimal cost, List<ServiceRequest> services, List<DeviceRequest> devices) {}
