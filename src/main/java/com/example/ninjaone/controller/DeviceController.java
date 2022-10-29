package com.example.ninjaone.controller;

import com.example.ninjaone.request.DeviceRequest;
import com.example.ninjaone.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(final DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public DeviceRequest addDevice(@Valid @RequestBody DeviceRequest deviceRequest){
        return deviceService.addEntity(deviceRequest);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<DeviceRequest> getAllDevice(){
        return deviceService.getAll();
    }
}
