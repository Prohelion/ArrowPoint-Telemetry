package au.com.teamarrow.service;

import org.springframework.transaction.annotation.Transactional;

import au.com.teamarrow.model.DataPoint;

@Transactional
public interface DataPointService {
    
    DataPoint get(Long id);
    
    DataPoint findByDataPointCanId(Integer dataPointCanId);
}
