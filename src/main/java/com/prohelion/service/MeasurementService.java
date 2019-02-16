package com.prohelion.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.prohelion.model.Measurement;

@Transactional
public interface MeasurementService {
    
    Measurement get(Long measurementId);

    List<Measurement> getMeasurementsForDevice(Long deviceId);

	List<Measurement> findAll();
}
