package com.prohelion.alerts;

import org.slf4j.LoggerFactory;

public class FlagData {

	private String device = null;
	private String flagName = null;
	private String canId = null;
	private Integer dataPointCanId = null;
	private Integer bit = null;
	private Integer flagType = null;
	private String details = null;
	private Double value = null;
	private Integer currentAlertLevel = AlertData.NORMAL;
	private Integer previouslyAlertedLevel = AlertData.NORMAL;
	private boolean supressAlerts = false;
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(FlagData.class);
	
	public void resetAlertSupression() {
		supressAlerts = false;
	}	
	
	public int getCurrentAlertLevel() {				
		return currentAlertLevel;
	}
	
	public Double getValue() {
		return value;
	}
	
	
	public byte getBit(int value, int position)
	{
	   return (byte) ((value >> position) & 1);
	}
	
	public void setValue(Double value) {
		this.value = value;
										
		// Remove any decimal places (there should be none anyway)
		int intValue = (int)value.doubleValue();		
		
		// If the flag is set
		if (getBit(intValue, bit) == 1) 
			currentAlertLevel = flagType;			
		else
			currentAlertLevel = AlertData.NORMAL;
			
		if ( previouslyAlertedLevel != currentAlertLevel && supressAlerts == false) {							
				
				String message = "Logical Error in Flagging if you see this message";

				switch (currentAlertLevel) {
					case AlertData.NORMAL: message = "alertLevel=INFO"; break;
					case AlertData.INFORMATION: message = "alertLevel=INFO"; break;
					case AlertData.WARNING: message = "alertLevel=WARNING"; break;
					case AlertData.ALERT: message = "alertLevel=ALERT"; break;
					case AlertData.SHUTDOWN: message = "alertLevel=SHUTDOWN"; break;
				}
				
				if (currentAlertLevel == AlertData.NORMAL) {
					message = message + " flag=\"" + details + "\" device=\"" + device + "\" flagName=\""+ flagName + "\" flagStatus=Cleared";
					message = message + " description=\"The flag '" + details + "' on device " + device + " ("+ flagName + ") has been cleared\"";
				} else {
					message = message + " flag=\"" + details + "\" device=\"" + device + "\" flagName=\""+ flagName + "\" flagStatus=Set";
					message = message + " description=\"The flag '" + details + "' on device " + device + " ("+ flagName + ") has been set\"";
				}
									
				if (previouslyAlertedLevel != currentAlertLevel) {
					switch (currentAlertLevel) {
						case AlertData.NORMAL: LOG.info(message); break;
						case AlertData.INFORMATION: LOG.info(message); break;
						case AlertData.WARNING: LOG.warn(message); break;
						case AlertData.ALERT: LOG.error(message); break;
						case AlertData.SHUTDOWN: LOG.error(message); break;
					}
					
				}
				
			previouslyAlertedLevel = currentAlertLevel;		
			supressAlerts = true;
			
		}
		
	}

	public void reset() {
		currentAlertLevel = AlertData.NORMAL;
		previouslyAlertedLevel = AlertData.NORMAL;		
		supressAlerts = false;		
	}
	
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	
	public String getFlagName() {
		return flagName;
	}
	public void setFlagName(String flagName) {
		this.flagName = flagName;
	}

	
	public String getCanId() {
		return canId;
	}
	public void setCanId(String canId) {
		this.canId = canId;
	}
	public Integer getDataPointCanId() {
		return dataPointCanId;
	}
	public void setDataPointCanId(Integer dataPointCanId) {
		this.dataPointCanId = dataPointCanId;
	}
	public Integer getBit() {
		return bit;
	}
	public void setBit(Integer bit) {
		this.bit = bit;
	}
	public Integer getFlagType() {
		return flagType;
	}
	public void setFlagType(Integer flagType) {
		this.flagType = flagType;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	
	
	
}
