package au.com.teamarrow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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
        return dataPointRepository.findOne(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public DataPoint findByDataPointCanId(Integer dataPointCanId) {
        return dataPointRepository.findByDataPointCanId(dataPointCanId);
    }
}
