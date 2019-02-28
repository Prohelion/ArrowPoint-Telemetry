package com.prohelion.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.prohelion.model.AbstractMeasurementData;
import com.prohelion.service.DataPointService;

@Controller
@RequestMapping(value = "/trend")
public class TrendDataController extends AbstractController {
            
    @Autowired
    private DataPointService dataPointService;
         
    @RequestMapping(value = "/{deviceId}/{term}/chart.html")
    public String getShortTermTrendChart(@PathVariable Integer deviceId, @PathVariable String term, Model model) {
        model.addAttribute("devices", getDevices());
        model.addAttribute("deviceId", deviceId);
        model.addAttribute("referenceUrl", String.format("/trend/%s/%s/data.json", deviceId, term));       
        model.addAttribute("dataPoint", dataPointService.findByDataPointCanId(deviceId));

        return "chart";
    }
            
    @RequestMapping(value = "/{deviceId}/{term}/data.json")
    public @ResponseBody List<? extends AbstractMeasurementData> getTrendData(@PathVariable Integer deviceId, @PathVariable String term) {
        
        return null;
    }
    
    @RequestMapping(value = "/{term}/power.html")
    public String getPowerChart(@PathVariable String term, Model model) {
        model.addAttribute("referenceUrl", String.format("/trend/%s/power.json", term));
        return "power";
    }
    
}
