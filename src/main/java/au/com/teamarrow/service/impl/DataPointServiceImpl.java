package au.com.teamarrow.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.com.teamarrow.dao.DataPointRepository;
import au.com.teamarrow.model.DataPoint;
import au.com.teamarrow.service.DataPointService;

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
