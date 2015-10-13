package au.com.teamarrow.alerts;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

public class AlertData {
	
	public final static int LOW_ALERT = 0;
	public final static int HIGH_ALERT = 1;
	
	public final static int NORMAL = 0;
	public final static int WARNING = 1;
	public final static int ALERT = 2;
	public final static int SHUTDOWN = 3;

	private Double value = null;
	private Double shutdownLevel = null;
	private Double alertLevel = null;
	private Double warningLevel = null;
	private String device = null;
	private String measurement = null;
	private String canId = null;
	private Integer dataPointCanId = null;
	private Integer alertType = null;
	
	private Integer currentAlertLevel = NORMAL;
	
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


	public void setValue(Double value) {
		this.value = value;
		
		int previousAlertLevel = currentAlertLevel;
			
		currentAlertLevel = NORMAL;
		String message = "Normal Operation";
			
		if (alertType == LOW_ALERT) {
			if ( value < warningLevel ) currentAlertLevel = WARNING;
			if ( value < alertLevel ) currentAlertLevel = ALERT;
			if ( value < shutdownLevel ) currentAlertLevel = SHUTDOWN;						
		}			
	
		if (alertType == HIGH_ALERT) {
			if ( value > warningLevel ) currentAlertLevel = WARNING;
			if ( value > alertLevel ) currentAlertLevel = ALERT;
			if ( value > shutdownLevel ) currentAlertLevel = SHUTDOWN;									
		}
				
			
		if ( previousAlertLevel != currentAlertLevel && supressAlerts == false) {
				
							
				if (currentAlertLevel != NORMAL ) {
			
					switch (currentAlertLevel) {
						case WARNING: message = "WARNING: The attribute: " + measurement + " on device: " + device + " at value: " + value;  break;
						case ALERT: message = "ALERT: The attribute: " + measurement + " on device: " + device + " at value: " + value;  break;
						case SHUTDOWN: message = "SHUTDOWN: The attribute: " + measurement + " on device: " + device + " at value: " + value;  break;
					}
								
					switch (alertType) {
						case LOW_ALERT: message = message + " has gone below "; break;
						case HIGH_ALERT: message = message + " has gone above "; break;
					}

					switch (currentAlertLevel) {
						case WARNING: message = message + "the WARNING threshold of: " + warningLevel; break;
						case ALERT: message = message + "the ALERT threshold of: " + alertLevel; break;
						case SHUTDOWN: message = message + "the SHUTDOWN threshold of: " + shutdownLevel; break;
					}
													
				} else
					message =  "INFO: The attribute: " + measurement + " on device: " + device + " has returned to a normal level";
								
				if (previousAlertLevel != currentAlertLevel) {
					switch (currentAlertLevel) {
						case NORMAL: log.info(message); break;
						case WARNING: log.warn(message); break;
						case ALERT: log.error(message); break;
						case SHUTDOWN: log.fatal(message); break;
					}
					
				}
		
			supressAlerts = true;
			
		}
		
	}


	public Double getShutdownLevel() {
		return shutdownLevel;
	}


	public void setShutdownLevel(Double shutdownLevel) {
		this.shutdownLevel = shutdownLevel;
	}


	public Double getAlertLevel() {
		return alertLevel;
	}


	public void setAlertLevel(Double alertLevel) {
		this.alertLevel = alertLevel;
	}


	public Double getWarningLevel() {
		return warningLevel;
	}


	public void setWarningLevel(Double warningLevel) {
		this.warningLevel = warningLevel;
	}


	public String getDevice() {
		return device;
	}


	public void setDevice(String device) {
		this.device = device;
	}


	public String getMeasurement() {
		return measurement;
	}


	public void setMeasurement(String measurement) {
		this.measurement = measurement;
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


	public Integer getAlertType() {
		return alertType;
	}


	public void setAlertType(Integer alertType) {
		this.alertType = alertType;
	}
		
	
}
