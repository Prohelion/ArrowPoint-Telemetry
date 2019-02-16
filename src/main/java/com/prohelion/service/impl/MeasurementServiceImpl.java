package com.prohelion.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prohelion.dao.DeviceRepository;
import com.prohelion.dao.MeasurementRepository;
import com.prohelion.model.Device;
import com.prohelion.model.Measurement;
import com.prohelion.service.MeasurementService;

@Service
public class MeasurementServiceImpl implements MeasurementService {

    @Autowired
    private DeviceRepository deviceRepository;
    
    @Autowired
    private MeasurementRepository measurementRepository;
    
    @Override
    @Transactional(readOnly = true)
    public Measurement get(Long measurementId) {
        return measurementRepository.findById(measurementId).orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Measurement> getMeasurementsForDevice(Long deviceId) {
        Device d = deviceRepository.findById(deviceId).orElse(null);
        
        List<Measurement> l = new ArrayList<Measurement>();
        l.addAll(d.getMeasurements());
        Collections.sort(l, new Comparator<Measurement>() {

            @Override
            public int compare(Measurement o1, Measurement o2) {
                return o1.getCanId().compareTo(o2.getCanId());
            }
        });
        
        return l;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Measurement> findAll() {
    	return measurementRepository.findAll();
    }
}
