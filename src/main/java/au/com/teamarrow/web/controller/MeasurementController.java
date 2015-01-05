package au.com.teamarrow.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import au.com.teamarrow.model.Measurement;
import au.com.teamarrow.service.MeasurementService;

@Controller
public class MeasurementController {
    
    @Autowired
    private MeasurementService measurementService;
    
    public MeasurementController() {
        
    }
    
    @Transactional
    @RequestMapping(value = { "/measurements.json" }, method = RequestMethod.GET)
    @ResponseBody
    public List<Measurement> getMeasurements(@RequestParam Long deviceId) { 
        return measurementService.getMeasurementsForDevice(deviceId);
    }
}
