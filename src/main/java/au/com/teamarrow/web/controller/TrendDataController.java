package au.com.teamarrow.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import au.com.teamarrow.model.AbstractMeasurementData;
import au.com.teamarrow.model.PowerUseDto;
import au.com.teamarrow.service.DataPointService;
import au.com.teamarrow.service.MeasurementDataService;

@Controller
@RequestMapping(value = "/trend")
public class TrendDataController extends AbstractController {
        
    @Autowired
    private MeasurementDataService measurementDataService;
    
    @Autowired
    private DataPointService dataPointService;
     
    /*@RequestMapping(value = "/short.html", params = {"deviceId"})
    public String getChart(@RequestParam Integer deviceId, Model model) {
        model.addAttribute("deviceId", deviceId);

        return "chart";
    }*/
    
    
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
        if ("short".equals(term)) {
            return measurementDataService.getShortTermTrendDataForDevice(deviceId);
        } else if ("medium".equals(term)) {
            return measurementDataService.getMediumTermTrendDataForDevice(deviceId);
        } else if ("long".equals(term)) {
            return measurementDataService.getLongTermTrendDataForDevice(deviceId);
        }
        
        return null;
    }
    
    @RequestMapping(value = "/{term}/power.html")
    public String getPowerChart(@PathVariable String term, Model model) {
        model.addAttribute("referenceUrl", String.format("/trend/%s/power.json", term));
        return "power";
    }
    
    @Transactional
    @RequestMapping(value = { "/{term}/power.json" })
    public @ResponseBody List<PowerUseDto> getPowerUse(@PathVariable String term) {
        if ("short".equals(term)) {
            return measurementDataService.getShortTermPowerData();
        } else if ("medium".equals(term)) {
            return measurementDataService.getMediumTermPowerData();
        } else if ("long".equals(term)) {
            return measurementDataService.getLongTermPowerData();
        }
        
        return null;
    }
}
