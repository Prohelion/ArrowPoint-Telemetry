package com.prohelion.splunk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.splunk.event.SplunkEvent;
import org.springframework.messaging.Message;

import com.prohelion.model.MeasurementData;

@MessageEndpoint
public class MeasurementDataToSplunkTransformer {
	
    private static final Logger LOG = LoggerFactory.getLogger(MeasurementDataToSplunkTransformer.class);

    private static String TIMESTAMP_KEY = "timestamp";
	private static String DATA_PNT_CAN_ID = "dataPointCanId";	
	private static String FVAL_KEY = "fval";
	private static String IVAL_KEY = "ival";
	private static String CVAL_KEY = "cval";
	  
	@Transformer
	public SplunkEvent processMessage(Message<MeasurementData> measurement) {
		
		    LOG.debug("Creating new SplunkEvent with data: " + measurement.toString());
		
			SplunkEvent data = new SplunkEvent();
			data.addPair(TIMESTAMP_KEY, measurement.getPayload().getTimestamp());		  
			data.addPair(DATA_PNT_CAN_ID, measurement.getPayload().getDataPointCanId());
			data.addPair(FVAL_KEY, measurement.getPayload().getFloatValue());
			data.addPair(IVAL_KEY, measurement.getPayload().getIntegerValue());
			data.addPair(CVAL_KEY, measurement.getPayload().getCharValue());			
			return data;
	}
	
}
