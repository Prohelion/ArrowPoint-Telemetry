package com.prohelion.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.prohelion.model.Device;
import com.prohelion.service.DeviceService;

@Controller
public abstract class AbstractController {

    @Autowired
    protected DeviceService deviceService;
    
    // @ModelAttribute - don't do this - the annotation makes it get called every JSON request which is bad
    public List<Device> getDevices() {
        return deviceService.getDevices();
    }
    
}
