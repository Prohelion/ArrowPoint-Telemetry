package com.prohelion.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.prohelion.model.DataPoint;

@Transactional
public interface DataPointService {
    
    DataPoint get(Long id);
    
    DataPoint findByDataPointCanId(Integer dataPointCanId);
    
    List<DataPoint> getDataPoints();
}
