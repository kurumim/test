package com.example.ninjaone.request;

import java.util.List;

public record ClientRequest(Long id, List<ServiceRequest> services, List<DeviceRequest> devices) {}
