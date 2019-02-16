package com.prohelion.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prohelion.dao.DataPointRepository;
import com.prohelion.model.DataPoint;
import com.prohelion.model.Measurement;
import com.prohelion.service.MeasurementService;

@Controller
public class DataPointController {
    
    @Autowired
    private MeasurementService measurementService;
        
    @Autowired
    private DataPointRepository dataPointRepository;
    
    @Transactional
    @RequestMapping(value = { "/datapoints.json" }, method = RequestMethod.GET, params = { "measurementId" })
    @ResponseBody
    public List<DataPoint> getDataPointsForMeasurement(@RequestParam(required = true) Long measurementId) {
        
        Measurement m = measurementService.get(measurementId);
        
        return dataPointRepository.findByMeasurement(m);
    }
    
    @Transactional
    @RequestMapping(value = { "/datapoints.json" }, method = RequestMethod.GET, params = { "dataPointCanId" })
    @ResponseBody
    public DataPoint getDataPoint(@RequestParam(required = true) int dataPointCanId) {
        return dataPointRepository.findByDataPointCanId(dataPointCanId);
    }
}
