package au.com.teamarrow.scheduled.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import au.com.teamarrow.model.MeasurementData;
import au.com.teamarrow.rules.Rule;
import au.com.teamarrow.rules.impl.CanAvailabilityCheck;
import au.com.teamarrow.rules.impl.CanStatusCheck;
import au.com.teamarrow.scheduled.Worker;
import au.com.teamarrow.service.MeasurementDataService;
	 
/**
 * An asynchronous worker
 */
@Transactional
@Component("canRulesEngineWorker")
public class CanRulesEngineWorker implements Worker {
	
	@Autowired
    private MeasurementDataService measurementDataService;
	
    	
	private List<Rule> rules;
		
	
	private static final Logger LOG = LoggerFactory.getLogger("Alerts");
	

	public CanRulesEngineWorker() {
	
		if (measurementDataService == null) LOG.debug("MEASUREMENT Data Service == null???");
	/*	
		//todo: Do this via a configuration file
		rules = new ArrayList<Rule>();
	
		rules.add(new CanAvailabilityCheck("Weather Systems Data", 0x3214, 10000, "Weather data is not arriving", "Weather data is back"));
		rules.add(new CanAvailabilityCheck("GPS Systems Data", 0x3310, 10000, "GPS data is not arriving", "GPS data is back") );
		rules.add(new CanAvailabilityCheck("Wave Sculpter Systems Data", 0x4020, 3000, "Wave Sculpter data is not arriving", "Wave Sculpter data is back") );
		rules.add(new CanAvailabilityCheck("Driver Control Systems Data", 0x5014, 3000, "Driver Control data is not arriving", "Wave Sculpter data is back") );
		rules.add(new CanAvailabilityCheck("Battery Management Systems Data", 0x6F44, 3000, "Battery Management data is not arriving", "Battery Management data is back") );
		
		rules.add(new CanAvailabilityCheck("MPPT 1 Systems Data", 0x7014, 3000, "MPPT 1 data is not arriving", "MPPT 1 data is back") );
		rules.add(new CanAvailabilityCheck("MPPT 2 Systems Data", 0x7054, 3000, "MPPT 2 data is not arriving", "MPPT 2 data is back") );
		rules.add(new CanAvailabilityCheck("MPPT 3 Systems Data", 0x7094, 3000, "MPPT 3 data is not arriving", "MPPT 3 data is back") );

		rules.add(new CanStatusCheck("Minimum Battery Voltage < 2.7v", 0x6F80, (double)2.7, (double)10, "Minimum Battery Below 2.7v", "Minimum Battery Voltage above 2.7v") );
		rules.add(new CanStatusCheck("Maximum Cell Temp > 40c", 0x6F92, (double)0, (double)40, "Max Cell Temp > 40c", "Max Cell Temp < 40c") );
		rules.add(new CanStatusCheck("Motor Temp > 70c", 0x40B0, (double)0, (double)70, "Motor Temp > 70c", "Motor Temp < 70c") );
		*/
	}
	
	public List<Rule> getRules() {
		return rules;
	}
	
	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	
	 @Async    
	public void work() {
					
		if (rules != null) 
			for (int i = 0; i < rules.size(); i++) {
				
				Rule rule = this.rules.get(i);
				
				try {
					
					MeasurementData measurementData = null;
					List<MeasurementData> measurementDatas = measurementDataService.findLatestDataForCanId(rule.getCanId());
					
					if (measurementDatas != null) measurementData = measurementDatas.get(0);
					
					rule.setMeasurementData(measurementData);
					rule.execute();
				} catch (Exception ex) { 
					LOG.debug("Rule " + rule.getRuleName() + " has thrown an exception");
				}
				
				if (rule.isStateChanged()) {
					if (rule.isFailed()) 
						LOG.warn(rule.getMessage());
					else
						LOG.info(rule.getMessage());
				}				
				
			}		 
		 
		return;
	}

}	
	
	

