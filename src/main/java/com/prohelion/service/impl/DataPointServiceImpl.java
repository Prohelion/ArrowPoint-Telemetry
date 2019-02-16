package com.prohelion.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prohelion.dao.DataPointRepository;
import com.prohelion.model.DataPoint;
import com.prohelion.service.DataPointService;

@Service
public class DataPointServiceImpl implements DataPointService {

    @Autowired
    private DataPointRepository dataPointRepository;
    
    @Override
    @Transactional(readOnly = true)
    public DataPoint get(Long id) {
        return dataPointRepository.findById(id).orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public DataPoint findByDataPointCanId(Integer dataPointCanId) {
        return dataPointRepository.findByDataPointCanId(dataPointCanId);
    }
           
    @Override
    @Transactional(readOnly = true)
    @Cacheable("dataPoints")
    public List<DataPoint> getDataPoints() {
        List<DataPoint> targetCollection = new ArrayList<DataPoint>();
        CollectionUtils.addAll(targetCollection, dataPointRepository.findAll().iterator());        
        return targetCollection;
    }
    
}
