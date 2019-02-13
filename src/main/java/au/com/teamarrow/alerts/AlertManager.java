package au.com.teamarrow.alerts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.LoggerFactory;

public class AlertManager {
	
	private Map<Integer,AlertData> alertBoundaries = null;
	private Map<String,FlagData> flagDetails = new ConcurrentHashMap<String,FlagData> ();;
	private Map<Integer,Boolean> flagsTracking = new ConcurrentHashMap<Integer,Boolean> ();
	private String normalLevelScript = null;
	private String warningLevelScript = null;
	private String alertLevelScript = null;
	private String shutdownLevelScript = null;
	private String scriptDir = null;
	private int previousAlertLevel = -1;
	private int supressionDelay = 3;
	private int supressionCounter = supressionDelay + 2;
	private boolean enableAlerts = true;
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AlertManager.class);
	
	public void setEnableAlerts(boolean enableAlerts) {
		this.enableAlerts = enableAlerts;
	}
	
	public void setSupressionDelay(int delay) {
		this.supressionDelay = delay;
		supressionCounter = supressionDelay + 2;
	}
	
	public void setFlagsFile(String flagsFile) {

		if (enableAlerts == false) return;
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		FlagData flagData = null;
		
		try {

			InputStream fis = null;
			
			// Try to load it from inside the package (war) first.... If that doesn't work look on the file system
			fis = this.getClass().getResourceAsStream(flagsFile);
			if (fis == null) fis = new FileInputStream(flagsFile); 			
			
			InputStreamReader isr = new InputStreamReader(fis);

			br = new BufferedReader(isr);

			// Remove the first line assuming that it is a header line
			br.readLine();
											
			while ((line = br.readLine()) != null) {

				String[] flagItem = line.split(cvsSplitBy);
				
				flagData = new FlagData();
				
				if (flagItem != null && flagItem.length > 0 &&  flagItem[0] != null && flagItem[0].length() != 0) { 									
												
					try {
					
						flagData.setDevice(flagItem[0]);
						flagData.setFlagName(flagItem[1]);
						flagData.setCanId(flagItem[2]);
						flagData.setDataPointCanId(new Integer(flagItem[3]));
						flagData.setBit(new Integer(flagItem[4]));	

						if (flagItem[5].toUpperCase().equals("INFO"))
							flagData.setFlagType(AlertData.INFORMATION);
						else if (flagItem[5].toUpperCase().equals("WARNING"))
							flagData.setFlagType(AlertData.WARNING);
						else if (flagItem[5].toUpperCase().equals("ALERT"))
							flagData.setFlagType(AlertData.ALERT);
						else if (flagItem[5].toUpperCase().equals("SHUTDOWN"))
							flagData.setFlagType(AlertData.SHUTDOWN);
						else
							throw new IOException("Bad flag type of " + flagItem[5] + " must be INFO, WARNING, ALERT or SHUTDOWN");						
						
						flagData.setDetails(flagItem[6]);

						flagDetails.put(flagData.getDataPointCanId() + "-" + flagData.getBit(),flagData);
						
						if (!flagsTracking.containsKey(flagData.getDataPointCanId()))
								flagsTracking.put(flagData.getDataPointCanId(), new Boolean(true));																		
						
					} catch (Exception ex) {
						// Bad data we just ignore on the load
						LOG.info("Bad data on flags load: " + ex.getMessage());
					}
				}						
					
			}

			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	
	
	public void setAlertsFile(String alertFile) {
		
		if (enableAlerts == false) return;
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		AlertData alertData = null;
		
		try {

			InputStream fis = null;
			alertBoundaries = new ConcurrentHashMap<Integer,AlertData> ();
			
			// Try to load it from inside the package (war) first.... If that doesn't work look on the file system
			fis = this.getClass().getResourceAsStream(alertFile);
			if (fis == null) fis = new FileInputStream(alertFile); 			
			
			InputStreamReader isr = new InputStreamReader(fis);

			br = new BufferedReader(isr);

			// Remove the first line assuming that it is a header line
			br.readLine();
											
			while ((line = br.readLine()) != null) {

				String[] alertItem = line.split(cvsSplitBy);
				
				alertData = new AlertData();
				
				if (alertItem != null && alertItem.length > 0 &&  alertItem[0] != null && alertItem[0].length() != 0) { 									
												
					try {
					
						alertData.setDevice(alertItem[0]);
						alertData.setMeasurement(alertItem[1]);
						alertData.setCanId(alertItem[2]);
						alertData.setDataPointCanId(new Integer(alertItem[3]));
						alertData.setWarningLevel(new Double(alertItem[4]));
						alertData.setAlertLevel(new Double(alertItem[5]));
						alertData.setShutdownLevel(new Double(alertItem[6]));
						
						if (alertItem[7].toUpperCase().equals("HIGH"))
							alertData.setAlertType(AlertData.HIGH_ALERT);
						else if (alertItem[7].toUpperCase().equals("LOW"))
							alertData.setAlertType(AlertData.LOW_ALERT);
						else if (alertItem[7].toUpperCase().equals("PERCENTAGE"))
							alertData.setAlertType(AlertData.PERCENTAGE_ALERT);
						else
							throw new IOException("Bad state value of " + alertItem[7] + " must be HIGH or LOW");
						
						alertBoundaries.put(alertData.getDataPointCanId(),alertData);
						
					} catch (Exception ex) {
						// Bad data we just ignore on the load
						LOG.info("Bad data on alerts load: " + ex.getMessage());
					}
				}											
			}

			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}	

	
	public void setDataPoint(Integer dataPointCanId, Double value) {
		
		if (!enableAlerts) return;	
		
		AlertData alertData = alertBoundaries.get(dataPointCanId);
		
		// We are not tracking this value, so return
		if (alertData != null) alertData.setValue(value);
		
		// Check to see if we are tracking this flag
		// if we are we do the loop
		if (flagsTracking.containsKey(dataPointCanId)) {			
			for (int i = 0; i <=7; i++) {
				FlagData flagData = flagDetails.get(dataPointCanId + "-" + i);
				if (flagData != null) flagData.setValue(value);
			}			
		}						
	}
	
	public int getCurrentAlertLevel() {
		
		int maxLevel = AlertData.NORMAL;

		for (Entry<Integer, AlertData> entry : alertBoundaries.entrySet()) {
			AlertData alertData = entry.getValue();
			if (alertData.getCurrentAlertLevel() > maxLevel) 
				maxLevel = alertData.getCurrentAlertLevel();						
		}
		
		for (Entry<String, FlagData> entry : flagDetails.entrySet()) {
			FlagData flagData = entry.getValue();
			if (flagData.getCurrentAlertLevel() > maxLevel) 
				maxLevel = flagData.getCurrentAlertLevel();								
		}
		
		return maxLevel;
		
	}

	
	public void resetAllSupression() {
				
		for (Entry<Integer, AlertData> entry : alertBoundaries.entrySet()) {
			AlertData alertData = entry.getValue();
			alertData.resetAlertSupression();				
		}
		
		for (Entry<String, FlagData> entry : flagDetails.entrySet()) {
			FlagData flagData = entry.getValue();
			flagData.resetAlertSupression();				
		}
			
	}

	
	public synchronized void triggerAlertScripts(boolean force) {
						
		// If alerts are not enabled then just return
		if (!enableAlerts) return;
		
		int  alertLevel = getCurrentAlertLevel();
		
		supressionCounter++;
		
		if (force == true  || (previousAlertLevel != alertLevel && (supressionCounter >= supressionDelay))) {
		
			switch (alertLevel) {
				case AlertData.NORMAL: 	executeCommand(normalLevelScript); 
										break;
				case AlertData.INFORMATION: 	executeCommand(normalLevelScript); 
												break;										
				case AlertData.WARNING: executeCommand(warningLevelScript);
										break;
				case AlertData.ALERT: 	executeCommand(alertLevelScript);
										break;
				case AlertData.SHUTDOWN: 	executeCommand(shutdownLevelScript);
											break;																			
			}
			
			previousAlertLevel = alertLevel;
			supressionCounter = 0;
			resetAllSupression();
			
		}
		
		// Reset suppression if we have had no alerts
		// safety check so we don't overflow
		if (supressionCounter >supressionDelay + 10 ) supressionCounter = 0;
		
	}


	public void resetAlerts() {
		previousAlertLevel = -1;

		for (Entry<Integer, AlertData> entry : alertBoundaries.entrySet()) {
			AlertData alertData = entry.getValue();
			alertData.reset();				
		}
		
		for (Entry<String, FlagData> entry : flagDetails.entrySet()) {
			FlagData flagData = entry.getValue();
			flagData.reset();				
		}

		triggerAlertScripts(true);		
	}
	
	
	public synchronized String executeCommand(String command) {

		StringBuffer output = new StringBuffer();
		String aux = "";
		String fullCommandPath = scriptDir + "/" + command;
		
		
		Process process;
		try {
			process = Runtime.getRuntime().exec(fullCommandPath,null,new File(scriptDir));

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((aux = reader.readLine()) != null) {
				output.append(aux);
			}					
			
			process.waitFor();
						
		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}

	public String getNormalLevelScript() {
		return normalLevelScript;
	}

	public void setNormalLevelScript(String normalLevelScript) {
		this.normalLevelScript = normalLevelScript;
	}

	public String getWarningLevelScript() {
		return warningLevelScript;
	}

	public void setWarningLevelScript(String warningLevelScript) {
		this.warningLevelScript = warningLevelScript;
	}

	public String getAlertLevelScript() {
		return alertLevelScript;
	}

	public void setAlertLevelScript(String alertLevelScript) {
		this.alertLevelScript = alertLevelScript;
	}

	public String getShutdownLevelScript() {
		return shutdownLevelScript;
	}

	public void setShutdownLevelScript(String shutdownLevelScript) {
		this.shutdownLevelScript = shutdownLevelScript;
	}

	public String getScriptDir() {
		return scriptDir;
	}

	public void setScriptDir(String scriptDir) {
		this.scriptDir = scriptDir;
	}

}
