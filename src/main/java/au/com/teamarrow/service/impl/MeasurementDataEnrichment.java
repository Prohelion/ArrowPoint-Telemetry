package au.com.teamarrow.service.impl;

import au.com.teamarrow.model.MeasurementData;

import java.util.ArrayList;
import java.util.List;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Splitter;
import org.springframework.messaging.Message;


@MessageEndpoint
public class MeasurementDataEnrichment {
	
	private Double array1Voltage = (double)-1;
	private Double array2Voltage = (double)-1;
	private Double array3Voltage = (double)-1;
	
	private Double array1Current = (double)-1;
	private Double array2Current = (double)-1;
	private Double array3Current = (double)-1;
	
	private Double busVoltage = (double)-1;
	private Double busCurrent = (double)-1;
	
    @Splitter
    public List<MeasurementData> enrichMeasurementDataMessages(Message<MeasurementData> message) {
    	
    	MeasurementData measurementData = message.getPayload();
    	
    	// We always return at least the message that got sent in
    	List<MeasurementData> returnList = new ArrayList<MeasurementData>();    	
    	returnList.add(measurementData);
    	
    	MeasurementData powerData = null;
    	MeasurementData totalPowerData = null;
    	
    	switch (measurementData.getDataPointCanId()) {
    	
	    	// Array 1 Amps
	    	case 0x7014: array1Current = measurementData.getFloatValue();
	    				 break;
	    	
	    	// Array 1 Volts
	    	case 0x7010: array1Voltage = measurementData.getFloatValue();
	    				 if (array1Current != -1) {
	    					 Double power = (array1Current / 1000) * (array1Voltage / 1000);
	    					 
	    					 // Create new item 3410
	    					 powerData = new MeasurementData(0x3410, measurementData.getTimestamp(), false, false, 8, power, 0, "", "");	    					 
	    				 }
	    				 break;
	    	
	    	// Array 2 Amps
	    	case 0x7054: array2Current = measurementData.getFloatValue();
	    				 break;
	    	
	    	// Array 2 Volts
	    	case 0x7050: array2Voltage = measurementData.getFloatValue();
						 if (array2Current != -1) {
							 Double power = (array2Current / 1000) * (array2Voltage / 1000);
							 
							 // Create new item 3420
							 powerData = new MeasurementData(0x3420, measurementData.getTimestamp(), false, false, 8, power, 0, "", "");	    					 
						 }	    	
	    				 break;
	    	
	    	// Array 3 Amps
	    	case 0x7094: array3Current = measurementData.getFloatValue();
	    				 break;
	    	
	    	// Array 3 Volts
	    	case 0x7090: array3Voltage = measurementData.getFloatValue();
						 if (array3Current != -1) {
							 Double power = (array3Current / 1000) * (array3Voltage / 1000);
							 
							 // Create new item 3430
							 powerData = new MeasurementData(0x3430, measurementData.getTimestamp(), false, false, 8, power, 0, "", "");	    					 
						 }
						 
						 
						 // Check if all array power data has been received and if so set the full array power
						 if (array1Current != -1 && array1Voltage != -1 &&
							 array2Current != -1 && array3Voltage != -1 &&
							 array2Current != -1 && array3Voltage != -1) {
							 Double power = ((array1Current / 1000) * (array1Voltage / 1000)) + 
									 		((array2Current / 1000) * (array2Voltage / 1000)) +
									 		((array3Current / 1000) * (array3Voltage / 1000));
							 
							 // Create new item 3440
							 totalPowerData = new MeasurementData(0x3440, measurementData.getTimestamp(), false, false, 8, power, 0, "", "");	    					 
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
							 powerData = new MeasurementData(0x3450, measurementData.getTimestamp(), false, false, 8, power, 0, "", "");	    					 
						 }

						 // Check if all array power data has been received and if so set the full array power
						 if (array1Current != -1 && array1Voltage != -1 &&
							 array2Current != -1 && array3Voltage != -1 &&
							 array2Current != -1 && array3Voltage != -1 &&
						 	 busCurrent != -1 && busVoltage != -1) {
							 
							 // Calculate array power
							 Double power = ((array1Current / 1000) * (array1Voltage / 1000)) + 
									 		((array2Current / 1000) * (array2Voltage / 1000)) +
									 		((array3Current / 1000) * (array3Voltage / 1000));
							 
							 // Subtract bus power
							 power = power - (busCurrent) * (busVoltage);
							 
							 // Create new item 3440
							 totalPowerData = new MeasurementData(0x3460, measurementData.getTimestamp(), false, false, 8, power, 0, "", "");	    					 
						 }	    		    	
						 
						 
						 
	    			     break;
    		    		
    			
    	}
    	
    	if (powerData != null) returnList.add(powerData);
    	if (totalPowerData != null) returnList.add(totalPowerData);
    	
    	
        return returnList;
    }
}