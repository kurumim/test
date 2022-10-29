package com.example.ninjaone.request;

import java.math.BigDecimal;
import java.util.List;

public record ClientRequest(Long id, BigDecimal cost, List<ServiceRequest> services, List<DeviceRequest> devices) {
}
