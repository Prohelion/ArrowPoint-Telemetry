package au.com.teamarrow.service.impl;

import au.com.teamarrow.alerts.AlertManager;
import au.com.teamarrow.maps.Route;
import au.com.teamarrow.maps.impl.NoRouteNodeException;
import au.com.teamarrow.model.MeasurementData;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Splitter;
import org.springframework.messaging.Message;

public class MeasurementDataEnrichment {
	
	private static final Logger LOG = LoggerFactory.getLogger(MeasurementDataEnrichment.class);
	
	private Integer array1Voltage = new Integer(-1);
	private Integer array2Voltage = new Integer(-1);
	private Integer array3Voltage = new Integer(-1);
	
	private Integer array1Current = new Integer(-1);
	private Integer array2Current = new Integer(-1);
	private Integer array3Current = new Integer(-1);
	
	private Double busVoltage = (double)-1;
	private Double busCurrent = (double)-1;
	
	private Double currentLat = (double)-1;
	private Double currentLong = (double)-1;
	
	private Double distanceRemaining = (double)-1;
	private Double totalDistance = (double)-1;
	
	private int velocityCount = 0;	
	private Double velocitySum = (double)0;
	
	@Autowired
	private Route route;	
	
	@Autowired    
    private AlertManager alertManager;

	public void setAlertManager(AlertManager alertManager) {
		this.alertManager = alertManager;
	}
	
	public void setCurrentLat(Double currentLat) {
		LOG.debug("Set currentLat" + currentLat);
		this.currentLat = currentLat;
	}

	public void setCurrentLong(Double currentLong) {
		LOG.debug("Set currentLong" + currentLong);
		this.currentLong = currentLong;
	}
	
	public void addVelocity(Double velocity) {
		LOG.debug("Add velcocity" + currentLong);
		
		if (velocityCount >= 300) {
			velocityCount = 0;
			velocitySum = (double)0;
		}
		
		velocitySum = velocitySum + velocity;
		velocityCount++;	
	}
	
	public Double getCurrentLat() {
		LOG.debug("Get currentLat" + currentLat);
		return currentLat;
	}
	
	public Double getCurrentLong() {	
		LOG.debug("Get currentLat" + currentLong);
		return currentLong;
	}
	
	public Double getAverageVelocity() {
		
		 if (velocityCount == 0) return((double)0);
		
		 return (velocitySum / velocityCount);
		
	}	
	
    @Splitter
    public List<MeasurementData> enrichMeasurementDataMessages(Message<MeasurementData> message) {
    	
    	MeasurementData measurementData = message.getPayload();
    	
    	// We always return at least the message that got sent in
    	List<MeasurementData> returnList = new ArrayList<MeasurementData>();    	
    	returnList.add(measurementData);
    	
    	// Send the data point to the alerts engine
    	alertManager.setDataPoint(measurementData.getDataPointCanId(), measurementData.getFloatValue());
    	
    	MeasurementData powerData = null;
    	MeasurementData totalPowerData = null;
    	MeasurementData distanceTravelledData = null;
    	MeasurementData distanceRemainingData = null;
    	
    	switch (measurementData.getDataPointCanId()) {
    	
    		// Vehicle velocity
    		case 0x4034: addVelocity(measurementData.getFloatValue());    					 
    					 break;
    					 
    		// Latitude
    		case 0x3310: setCurrentLat(measurementData.getFloatValue());
    					 break;
    			
    		// Longitude
    		case 0x3314: setCurrentLong(measurementData.getFloatValue());
    				     if (getCurrentLat() != -1 && getCurrentLong() != -1) {
    				    	 try {
								route.gotoNearestNode(getCurrentLat(), getCurrentLong());
								distanceRemaining = route.getTotalDistanceRemaining();
								
								if (totalDistance == -1) totalDistance = route.getTotalDistanceTravelled();
								
								double distanceTravelled = totalDistance - distanceRemaining;
								
								// Create new item 3470
								distanceTravelledData = new MeasurementData(0x3470, measurementData.getTimestamp(), false, false, 8, distanceTravelled, 0, "", "Normal");

								// Create new item 3474
								distanceRemainingData = new MeasurementData(0x3474, measurementData.getTimestamp(), false, false, 8, distanceRemaining, 0, "", "Normal");
								
							} catch (NoRouteNodeException e) {
								e.printStackTrace();
							}
    		
    				     }
    					 break;
    	
	    	// Array 1 Amps
	    	case 0x7014: array1Current = measurementData.getIntegerValue();
	    				 break;
	    	
	    	// Array 1 Volts
	    	case 0x7010: array1Voltage = measurementData.getIntegerValue();
	    				 if (array1Current != -1) {
	    					 Double power = (double)(array1Current * array1Voltage) / 1000000;
	    					 
	    					 // Create new item 3410
	    					 powerData = new MeasurementData(0x3410, measurementData.getTimestamp(), false, false, 8, power, 0, "", "Normal");	    					 
	    				 }
	    				 break;
	    	
	    	// Array 2 Amps
	    	case 0x7054: array2Current = measurementData.getIntegerValue();
	    				 break;
	    	
	    	// Array 2 Volts
	    	case 0x7050: array2Voltage = measurementData.getIntegerValue();
						 if (array2Current != -1) {
							 Double power = (double)(array2Current * array2Voltage) / 1000000;
							 
							 // Create new item 3420
							 powerData = new MeasurementData(0x3420, measurementData.getTimestamp(), false, false, 8, power, 0, "", "Normal");	    					 
						 }	    	
	    				 break;
	    	
	    	// Array 3 Amps
	    	case 0x7094: array3Current = measurementData.getIntegerValue();
	    				 break;
	    	
	    	// Array 3 Volts
	    	case 0x7090: array3Voltage = measurementData.getIntegerValue();
						 if (array3Current != -1) {
							 Double power = (double)(array3Current * array3Voltage) / 1000000;
							 
							 // Create new item 3430
							 powerData = new MeasurementData(0x3430, measurementData.getTimestamp(), false, false, 8, power, 0, "", "Normal");	    					 
						 }
						 						 
						 // Check if all array power data has been received and if so set the full array power
						 if (array1Current != -1 && array1Voltage != -1 &&
							 array2Current != -1 && array2Voltage != -1 &&
							 array3Current != -1 && array3Voltage != -1) {
							 Double power = (double)((array1Current * array1Voltage) + 
									 		 		 (array2Current * array2Voltage) +
									 		 		 (array3Current * array3Voltage)) / 1000000;
							 
							 // Create new item 3440
							 totalPowerData = new MeasurementData(0x3440, measurementData.getTimestamp(), false, false, 8, power, 0, "", "Normal");
							 
							 // Check as we have been seeing weird data
							 if (power < 0) {
								 LOG.error("Calulated Total Array power ({}) is in error: Array1Current {}, Array1Voltage {}, Array2Current {}, Array2Voltage {}, Array3Current {}, Array3Voltage", power, array1Current, array1Voltage, array2Current, array2Voltage, array3Current, array3Voltage  );
							 }
							 
							 
						 }	    		    	

						 
	    				 break;
    	
	    	// Bus Current
	    	case 0x4024: busCurrent = measurementData.getFloatValue();
	    				 break;
    	
	    	// Bus Voltage
	    	case 0x4020: busVoltage = measurementData.getFloatValue();
						 if (busCurrent != -1) {
							 Double power = (busCurrent) * (busVoltage);
							 
							 // Create new item 3450
							 powerData = new MeasurementData(0x3450, measurementData.getTimestamp(), false, false, 8, power, 0, "", "Normal");	    					 
						 }

						 // Check if all array power data has been received and if so set the full array power
						 if (array1Current != -1 && array1Voltage != -1 &&
							 array2Current != -1 && array2Voltage != -1 &&
							 array3Current != -1 && array3Voltage != -1 &&
						 	 busCurrent != -1 && busVoltage != -1) {
							 
							 // Calculate array power
							 Double power = (double)((array1Current * array1Voltage) + 
					 		 		 				 (array2Current * array2Voltage) +
					 		 		 				 (array3Current * array3Voltage)) / 1000000;
			  
							 // Subtract bus power
							 power = power - (busCurrent * busVoltage);
							 
							 // Create new item 3440
							 totalPowerData = new MeasurementData(0x3460, measurementData.getTimestamp(), false, false, 8, power, 0, "", "Normal");	    					 
						 }	    		    	
						 						 					
	    			     break;
    		    		    			
    	}
    	
    	if (powerData != null) returnList.add(powerData);
    	if (totalPowerData != null) returnList.add(totalPowerData);
    	if (distanceTravelledData != null) returnList.add(distanceTravelledData);
    	if (distanceRemainingData != null) returnList.add(distanceRemainingData);    	
    	
        return returnList;
    }
}