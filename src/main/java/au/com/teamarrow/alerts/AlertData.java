package au.com.teamarrow.alerts;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

public class AlertData {
	
	public final static int LOW_ALERT = 0;
	public final static int HIGH_ALERT = 1;
	public final static int PERCENTAGE_ALERT = 2;
	
	public final static int NORMAL = 0;
	public final static int INFORMATION = 1;
	public final static int WARNING = 2;
	public final static int ALERT = 3;
	public final static int SHUTDOWN = 4;

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
	private Integer previouslyAlertedLevel = NORMAL;
	
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
		
		currentAlertLevel = NORMAL;
			
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
			
		if ( previouslyAlertedLevel != currentAlertLevel && supressAlerts == false) {
					
				String message = "Logical Error in Alerting if you see this message";
			
				switch (currentAlertLevel) {
					case AlertData.NORMAL: message = "alertLevel=INFO"; break;
					case AlertData.INFORMATION: message = "alertLevel=INFO"; break;
					case AlertData.WARNING: message = "alertLevel=WARNING"; break;
					case AlertData.ALERT: message = "alertLevel=ALERT"; break;
					case AlertData.SHUTDOWN: message = "alertLevel=SHUTDOWN"; break;
				}
				
				message = message + " attribute=\"" + measurement + "\" device=\"" + device + "\" value=" + value; 
				
				switch (currentAlertLevel) {
					case NORMAL: message = message + " thresholdType=NORMAL threshold=" + warningLevel; break;
					case WARNING: message = message + " thresholdType=WARNING threshold=" + warningLevel; break;
					case ALERT: message = message + " thresholdType=ALERT threshold=" + alertLevel; break;
					case SHUTDOWN: message = message + " thresholdType=SHUTDOWN threshold=" + shutdownLevel; break;
				}
				
				if (currentAlertLevel != NORMAL ) {
			
					switch (currentAlertLevel) {
						case WARNING: message = message + " description=\"The attribute: " + measurement + " on device: " + device + " at value: " + value;  break;
						case ALERT: message =  message + " description=\"The attribute: " + measurement + " on device: " + device + " at value: " + value;  break;
						case SHUTDOWN: message =  message + " description=\"The attribute: " + measurement + " on device: " + device + " at value: " + value;  break;
					}
								
					switch (alertType) {
						case LOW_ALERT: message = message + " has gone below "; break;
						case HIGH_ALERT: message = message + " has gone above "; break;
					}

					switch (currentAlertLevel) {
						case WARNING: message = message + "the WARNING threshold of: " + warningLevel + "\""; break;
						case ALERT: message = message + "the ALERT threshold of: " + alertLevel + "\""; break;
						case SHUTDOWN: message = message + "the SHUTDOWN threshold of: " + shutdownLevel + "\""; break;
					}
													
				} else
					message =  message + " description=\"The attribute: " + measurement + " on device: " + device + " has returned to a normal level\"";
								
				if (previouslyAlertedLevel != currentAlertLevel) {
					switch (currentAlertLevel) {
						case NORMAL: log.info(message); break;
						case WARNING: log.warn(message); break;
						case ALERT: log.error(message); break;
						case SHUTDOWN: log.fatal(message); break;
					}
					
				}
		
			previouslyAlertedLevel = currentAlertLevel;
			supressAlerts = true;
			
		}
		
	}


	public void reset() {
		currentAlertLevel = NORMAL;
		previouslyAlertedLevel = NORMAL;		
		supressAlerts = false;		
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
