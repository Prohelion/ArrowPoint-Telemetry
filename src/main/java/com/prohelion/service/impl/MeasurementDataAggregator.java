package com.prohelion.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prohelion.model.MeasurementData;

public class MeasurementDataAggregator {
	
	private static final Logger LOG = LoggerFactory.getLogger(MeasurementDataAggregator.class);
		
	public MeasurementData collect(List<MeasurementData> payloads) {
		LOG.debug("Received " + payloads.size() + " in the aggregator for id " + payloads.get(0).getDataPointCanId() );   
		   
		//  Returns just the last and hence most recent element
		return payloads.get(payloads.size() - 1);  
	}

}
