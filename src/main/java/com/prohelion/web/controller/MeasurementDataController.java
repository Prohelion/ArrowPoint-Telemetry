package com.prohelion.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prohelion.model.MeasurementData;
import com.prohelion.service.MeasurementDataService;

@Controller
@RequestMapping(value = "/")
public class MeasurementDataController {
    
    @Autowired
    private MeasurementDataService measurementDataService;
    
    @Transactional
    @RequestMapping(value = { "/snapshot.json" }, method = RequestMethod.GET, params = { "deviceId" })
    @ResponseBody
    public List<MeasurementData> getTelemetrySnapshot(@RequestParam(required = true) Integer deviceId)
    {
        return measurementDataService.findLatestDataForCanId(deviceId >> 4);
    }
    
    @Transactional
    @RequestMapping(value = { "/snapshot.html" }, method = RequestMethod.GET, params = { "deviceId" })
    public String getTelemetrySnapshot(@RequestParam(required = true) Integer deviceId, Model model)
    {
        model.addAttribute("telemetry", measurementDataService.findLatestDataForCanId(deviceId >> 4));
        
        return "snapshot";
    }
    
    @Transactional
    @RequestMapping(value = { "/measurement-data.json" }, method = RequestMethod.GET, params = { "baseId", "offset", "dataPointOffset" })
    @ResponseBody
    public List<MeasurementData> getTelemetryForDevice(@RequestParam(required = true) Integer baseId, @RequestParam(required = true) Integer offset, @RequestParam(required = true) Integer dataPointOffset) {   
        return measurementDataService.getMeasurementsForDevice((baseId + offset << 4) | dataPointOffset);
    }
    
    @Transactional
    @RequestMapping(value = { "/measurement-data.json" }, method = RequestMethod.GET, params = { "deviceId" })
    @ResponseBody
    public List<MeasurementData> getTelemetryForDevice(@RequestParam(required = true) Integer deviceId) {   
        return measurementDataService.getMeasurementsForDevice(deviceId);
    }
    
}
