package au.com.teamarrow.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.com.teamarrow.dao.LongTermTrendDataRepository;
import au.com.teamarrow.dao.MeasurementDataRepository;
import au.com.teamarrow.dao.MediumTermTrendDataRepository;
import au.com.teamarrow.dao.ShortTermTrendDataRepository;
import au.com.teamarrow.model.LongTermTrendData;
import au.com.teamarrow.model.MeasurementData;
import au.com.teamarrow.model.MediumTermTrendData;
import au.com.teamarrow.model.PowerUseDto;
import au.com.teamarrow.model.ShortTermTrendData;
import au.com.teamarrow.service.MeasurementDataService;

@Service("measurementDataService")
@Transactional
public class MeasurementDataServiceImpl implements MeasurementDataService {

    private static final Logger LOG = LoggerFactory.getLogger(MeasurementDataServiceImpl.class);
    
    @Autowired
    private MeasurementDataRepository measurementDataRepository;
    
    @Autowired
    private ShortTermTrendDataRepository shortTermTrendDataRepository;
    
    @Autowired
    private MediumTermTrendDataRepository mediumTermTrendDataRepository;

    @Autowired
    private LongTermTrendDataRepository longTermTrendDataRepository;
    
    @Autowired 
    private CacheManager cacheManager;
    
    private Sort sort = new Sort(Sort.Direction.DESC, "timestamp");
    
    @Override
    public List<MeasurementData> getMeasurementsForDevice(Integer deviceId) {
        
        Page<MeasurementData> data = measurementDataRepository.findByDataPointCanId(deviceId, new PageRequest(0, 500, sort));
        
        return data.getContent();
    }
    
    @Override
    public List<ShortTermTrendData> getShortTermTrendDataForDevice(Integer deviceId) {
        
        Page<ShortTermTrendData> data = shortTermTrendDataRepository.findByDataPointCanId(deviceId, new PageRequest(0, 500, sort));
        
        return data.getContent();
    }
    
    @Override
    public List<MediumTermTrendData> getMediumTermTrendDataForDevice(Integer deviceId) {
        
        Page<MediumTermTrendData> data = mediumTermTrendDataRepository.findByDataPointCanId(deviceId, new PageRequest(0, 50000, sort));
        
        return data.getContent();
    }
    
    @Override
    public List<LongTermTrendData> getLongTermTrendDataForDevice(Integer deviceId) {
        
        /*Page<LongTermTrendData> data = longTermTrendDataRepository.findByDataPointCanId(deviceId, new PageRequest(0, 500, sort));
        
        return data.getContent();*/
        
        return longTermTrendDataRepository.getTrendDataForDay(deviceId, DateMidnight.now());
    }
    

	@Override    
    public List<MeasurementData> findLatestDataForCanId(Integer canId) {
    	    	    	    	
    	List<MeasurementData> measurementData = null;
    	
        Cache c = cacheManager.getCache("LatestMeasurement");
        if (c.get(canId) != null) {        	        	        	        
        	measurementData = (ArrayList<MeasurementData>) c.get(canId).get();
        } else {        	
        	  LOG.debug("No cached data found for id " + canId);        	 
        	  measurementData =  measurementDataRepository.findLatestDataForCanId(canId);
        	  c.put(canId, measurementData);
        }        
    	
		return measurementData;		        
    }
    
    @Override
    public List<PowerUseDto> getShortTermPowerData() {
        return measurementDataRepository.findShortTermPowerUseData();
    }
    
    @Override
    public List<PowerUseDto> getMediumTermPowerData() {
        List<PowerUseDto> a = measurementDataRepository.findMediumTermPowerUseData(); 
        
        return a;
    }
    
    @Override
    public List<PowerUseDto> getLongTermPowerData() {
        return measurementDataRepository.findLongTermPowerUseData();
    }

    @ServiceActivator
    public void processMeasurementData(Message<MeasurementData> message) {
        MeasurementData md = (MeasurementData)message.getPayload();
        
         measurementDataRepository.save(md);
        
        // Now place the measurement data in the cache, we are doing this
        // using code rather than annotations due to a limitation in 
        // the spring annotations that require you to return the thing you 
        // want to cache.
        Cache c = cacheManager.getCache("LatestMeasurement");
        if (c != null) {
        	
          Integer canId = md.getDataPointCanId() >> 4;
          
          List<MeasurementData> measurementDataList = null;
        	
          // If there is nothing in the cache we will not try and add something
          // we let the structure get populated by the database          
          if (c.get(canId) != null) {        	        	        	        
          		measurementDataList = (ArrayList<MeasurementData>) c.get(canId).get();          		
          	
          		for (int i = 0; i < measurementDataList.size(); i++) {
          			MeasurementData dataFromList = measurementDataList.get(i);
          	    
          			if ( dataFromList.getDataPointCanId().intValue() == md.getDataPointCanId().intValue() )
          				measurementDataList.set(i, md);          	    
          		}
          	
          		c.put(canId, measurementDataList);
          	
          } 
                                           
        }
        
    }
}
