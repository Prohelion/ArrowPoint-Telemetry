package au.com.teamarrow.alerts;

import org.apache.log4j.Logger;

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

	private boolean supressAlerts = false;
	
	static Logger log = Logger.getLogger("Alerts");
	
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
		
		int previousAlertLevel = currentAlertLevel;
			
		currentAlertLevel = AlertData.NORMAL;
		String message = "Normal Operation";
		
		// Remove any decimal places (there should be none anyway)
		int intValue = (int)value.doubleValue();		
		
		// If the flag is set
		if (getBit(intValue, bit) == 1) 
			currentAlertLevel = flagType;			
		else
			currentAlertLevel = AlertData.NORMAL;
			
		if ( previousAlertLevel != currentAlertLevel && supressAlerts == false) {							
				
				if (currentAlertLevel == AlertData.NORMAL)
					message = "The flag '" + details + "' on device: " + device + " ("+ flagName + ") has been cleared";
				else
					message = "The flag '" + details + "' on device: " + device + " ("+ flagName + ") has been set";
									
				if (previousAlertLevel != currentAlertLevel) {
					switch (currentAlertLevel) {
						case AlertData.NORMAL: log.info("INFO: " + message); break;
						case AlertData.WARNING: log.warn("WARNING: " + message); break;
						case AlertData.ALERT: log.error("ALERT: " + message); break;
						case AlertData.SHUTDOWN: log.fatal("SHUTDOWN: " + message); break;
					}
					
				}
		
			supressAlerts = true;
			
		}
		
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
