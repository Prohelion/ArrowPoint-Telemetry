package au.com.teamarrow.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import au.com.teamarrow.model.DataPoint;

@Transactional
public interface DataPointService {
    
    DataPoint get(Long id);
    
    DataPoint findByDataPointCanId(Integer dataPointCanId);
    
    List<DataPoint> getDataPoints();
}
