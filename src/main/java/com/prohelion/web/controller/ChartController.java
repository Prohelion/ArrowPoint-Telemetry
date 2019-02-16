package com.prohelion.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prohelion.model.DataPoint;
import com.prohelion.service.DataPointService;

@Transactional
@Controller
public class ChartController extends AbstractController {
       
    @Autowired
    private DataPointService dataPointService;
     
    @RequestMapping(value = "/chart.html", params = {"deviceId"})
    public String getChart(@RequestParam Integer deviceId, Model model) {
        model.addAttribute("devices", getDevices());
        model.addAttribute("deviceId", deviceId);
        model.addAttribute("referenceUrl", "/measurement-data.json");
        
        // model.addAttribute("dataPoint", dataPointService.findByDataPointCanId(deviceId));
      
        return "chart";
    }
    
    @RequestMapping(value = "/{termLength}-term.html", params = {"deviceId"})
    public String getTrendChart(@RequestParam Integer deviceId, @PathVariable String termLength, Model model) {
        model.addAttribute("devices", getDevices());
        model.addAttribute("deviceId", deviceId);
        model.addAttribute("referenceUrl", String.format("/trend/%s-term/data.json", termLength));

        return "chart";
    }
    
    /*@RequestMapping(value = "/short-term.html", params = {"deviceId"})
    public String getShortTermTrendChart(@RequestParam Integer deviceId, Model model) {
        model.addAttribute("devices", getDevices());
        model.addAttribute("deviceId", deviceId);
        model.addAttribute("referenceUrl", "/trend/short-term/data.json");

        return "chart";
    }
    
    @RequestMapping(value = "/mediunm-term.html", params = {"deviceId"})
    public String getMediumTermTrendChart(@RequestParam Integer deviceId, Model model) {
        model.addAttribute("devices", getDevices());
        model.addAttribute("deviceId", deviceId);
        model.addAttribute("referenceUrl", "/trend/medium-term/data.json");

        return "chart";
    }
    
    @RequestMapping(value = "/long-term.html", params = {"deviceId"})
    public String getLongTermTrendChart(@RequestParam Integer deviceId, Model model) {
        model.addAttribute("devices", getDevices());
        model.addAttribute("deviceId", deviceId);
        model.addAttribute("referenceUrl", "/trend/long-term/data.json");

        return "chart";
    }*/
    
    @ModelAttribute("dataPoint")
    public @ResponseBody DataPoint getDataPoint(@RequestParam Integer deviceId) {
        return dataPointService.findByDataPointCanId(deviceId);
    }
}
