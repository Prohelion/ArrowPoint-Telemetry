package au.com.teamarrow.rules.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import au.com.teamarrow.model.MeasurementData;
import au.com.teamarrow.rules.Rule;
import au.com.teamarrow.service.MeasurementDataService;

public class CanStatusCheck implements Rule {

    private static final Logger LOG = LoggerFactory.getLogger(CanStatusCheck.class);
	
	String ruleName;
	Integer canId;
	Double maxValue;
	Double minValue;
	String failureMsg; 
	String successMsg;
	String currentMsg = null;
	boolean isFailed = false;
	boolean wasFailed = false;	
		
	MeasurementData measurementData = null;	
	
    @Autowired
    private MeasurementDataService measurementDataService;
    
    public void setMeasurementDataService(MeasurementDataService measurementDataService)
    { 
    	this.measurementDataService = measurementDataService;
    }
    
    public CanStatusCheck() {
    	
    }
    
	public CanStatusCheck(String ruleName, Integer canId, Double minValue, Double maxValue, String failureMsg, String successMsg) {
		this.ruleName = ruleName;
		this.canId = canId;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.failureMsg = failureMsg;
		this.successMsg = successMsg;
	}
	
	@Override
	public String getRuleName() {		
		return this.ruleName;
	}

	@Override
	public String getMessage() {
		return currentMsg;
	}

	@Override
	public boolean isFailed() {
		return isFailed;
	}

	@Override
	public boolean isStateChanged() {		
		if (isFailed != wasFailed) return true;
		return false;
	}

	@Override
	public Integer getCanId() {
		return canId;
	}
	
	@Override
	public void setMeasurementData(MeasurementData measurementData) {
		this.measurementData = measurementData;
	}
	

	@Override
	public void execute() {

		LOG.debug("Rule: [" + ruleName + "] looking for latest can data for: " + canId);

		wasFailed = isFailed;
		
		if ( measurementData == null ) {
			List<MeasurementData> data = measurementDataService.findLatestDataForCanId(canId);
			measurementData = data.get(0);
		}
				
		isFailed = false;		
		
		if (measurementData == null) isFailed = true;
		else {
			Double canValue = measurementData.getFloatValue();				
			if (canValue < minValue || canValue > maxValue) isFailed = true;
		}
				
        if (isFailed) currentMsg = failureMsg; else currentMsg = successMsg;
        
	}

}
