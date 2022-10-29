package com.example.ninjaone.controller.request;

import java.util.List;

public record ClientRequest(Long id, List<ServiceRequest> services, List<DeviceRequest> devices) {}
