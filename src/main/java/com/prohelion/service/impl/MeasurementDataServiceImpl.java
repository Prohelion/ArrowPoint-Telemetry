package com.prohelion.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.Message;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.prohelion.dao.MeasurementDataRepository;
import com.prohelion.model.MeasurementData;
import com.prohelion.service.MeasurementDataService;

@Service("measurementDataService")
@Transactional
public class MeasurementDataServiceImpl implements MeasurementDataService {

    private static final Logger LOG = LoggerFactory.getLogger(MeasurementDataServiceImpl.class);
    
    @Autowired
    private MeasurementDataRepository measurementDataRepository;
        
    @Autowired 
    private CacheManager cacheManager;
    
    private Sort sort = new Sort(Sort.Direction.DESC, "timestamp");
    
    @Override
    public List<MeasurementData> getMeasurementsForDevice(Integer deviceId) {
        
        Page<MeasurementData> data = measurementDataRepository.findByDataPointCanId(deviceId, PageRequest.of(0, 500, sort));
        
        return data.getContent();
    }              

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
    
	@SuppressWarnings("unchecked")
	@Override    
    public List<MeasurementData> findLatestDataForCanId(Integer canId) {
    	    	    	    	
    	List<MeasurementData> measurementData = null;
    	
        Cache c = cacheManager.getCache("LatestMeasurement");
        if (c.get(canId) != null) {        	        	        	        
        	measurementData = (ArrayList<MeasurementData>) c.get(canId).get();
        } else {        	
        	  LOG.debug("No cached data found for id " + canId);        	 
        	  measurementData =  measurementDataRepository.findLatestDataForCanId(canId);
        	  
        	  // It is possible that if multiple events trigger at exactly the same time you can get duplicates
        	  // This is very unlikely but it an occur.  To address the issue we filter them out here 
        	  // as filtering them out in the SQL has performance implications
        	  List<MeasurementData> measurementDataListWithoutDuplicates = measurementData.stream().filter(distinctByKey(MeasurementData::getDataPointCanId)).collect(Collectors.toList());
        	  
        	  if (measurementDataListWithoutDuplicates != null && !measurementDataListWithoutDuplicates.isEmpty())
        		  c.put(canId, measurementDataListWithoutDuplicates);
        	  
        	  return measurementDataListWithoutDuplicates;
        }        
    	
		return measurementData;		        
    }
    
    @SuppressWarnings("unchecked")
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
