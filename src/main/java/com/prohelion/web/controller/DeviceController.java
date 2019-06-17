package com.prohelion.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.prohelion.model.Device;
import com.prohelion.service.DeviceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController 
@Api(value="Device")
public class DeviceController {
    
    @Autowired
    private DeviceService deviceService;
    
    public DeviceController() {
        
    }      
    
    @Transactional(readOnly = true)
    @ApiOperation("Returns list of all Devices in the system.")
    @RequestMapping(value = { "/devices.json" }, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Device> getDevices(Model model) { 
        return deviceService.getDevices();
    }
}
