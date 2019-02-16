package com.prohelion.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prohelion.model.Device;
import com.prohelion.service.DeviceService;

@Controller
public class DeviceController {
    
    @Autowired
    private DeviceService deviceService;
    
    public DeviceController() {
        
    }
    
    @RequestMapping(value = "/device.html", method = RequestMethod.GET)
    public String getDevice()
    {
        return "device";
    }
    
    @Transactional(readOnly = true)
    @RequestMapping(value = { "/devices.json" }, method = RequestMethod.GET)
    @ResponseBody
    public List<Device> getDevices(Model model) { 
        return deviceService.getDevices();
    }
}
