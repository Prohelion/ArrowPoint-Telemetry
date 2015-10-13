package au.com.teamarrow.alerts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

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
	
	static Logger log = Logger.getLogger("Alerts");		
	
	public void setSupressionDelay(int delay) {
		this.supressionDelay = delay;
		supressionCounter = supressionDelay + 2;
	}
	
	public void setFlagsFile(String flagsFile) {

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
												
						if (flagItem[5].toUpperCase().equals("WARNING"))
							flagData.setFlagType(AlertData.WARNING);
						if (flagItem[5].toUpperCase().equals("ALERT"))
							flagData.setFlagType(AlertData.ALERT);
						if (flagItem[5].toUpperCase().equals("SHUTDOWN"))
							flagData.setFlagType(AlertData.SHUTDOWN);
						
						flagData.setDetails(flagItem[6]);

						flagDetails.put(flagData.getDataPointCanId() + "-" + flagData.getBit(),flagData);
						
						if (!flagsTracking.containsKey(flagData.getDataPointCanId()))
								flagsTracking.put(flagData.getDataPointCanId(), new Boolean(true));						
						
						
						
					} catch (Exception ex) {
						// Bad data we just ignore on the load
						log.info("Bad data on flags load");
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
						
						if (alertItem[7].toUpperCase().equals("LOW"))
							alertData.setAlertType(AlertData.LOW_ALERT);
						
						alertBoundaries.put(alertData.getDataPointCanId(),alertData);
						
					} catch (Exception ex) {
						// Bad data we just ignore on the load
						log.info("Bad data on alerts load");
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
		
		Collection<AlertData> alerts = alertBoundaries.values();
		
		Iterator<AlertData> AlertIterator = alerts.iterator();
		while (AlertIterator.hasNext()) {
			AlertData alertData = AlertIterator.next();
			
			if (alertData.getCurrentAlertLevel() > maxLevel) 
				maxLevel = alertData.getCurrentAlertLevel();		
		}
			
		
		Collection<FlagData> flags = flagDetails.values();
		
		Iterator<FlagData> flagIterator = flags.iterator();
		while (flagIterator.hasNext()) {
			FlagData flagData = flagIterator.next();
			
			if (flagData.getCurrentAlertLevel() > maxLevel) 
				maxLevel = flagData.getCurrentAlertLevel();		
		}	
		
		return maxLevel;
		
	}

	
	public void resetAllSupression() {
		
		Collection<AlertData> alerts = alertBoundaries.values();
		
		Iterator<AlertData> iterator = alerts.iterator();
		while (iterator.hasNext()) {
			AlertData alertData = iterator.next();			
			alertData.resetAlertSupression();
		}

		for (Entry<String, FlagData> entry : flagDetails.entrySet()) {
			FlagData flagData = entry.getValue();
			flagData.resetAlertSupression();				
		}

/*		
		Collection<FlagData> flags = flagDetails.values();
		
		Iterator<FlagData> flagIterator = flags.iterator();
		while (iterator.hasNext()) {
			FlagData flagData = flagIterator.next();			
			flagData.resetAlertSupression();
		}
*/
			
	}

	
	public synchronized void triggerAlertScripts() {
						
		int  alertLevel = getCurrentAlertLevel();
		
		supressionCounter++;
		
		if (previousAlertLevel != alertLevel && (supressionCounter >= supressionDelay)) {
		
			switch (alertLevel) {
				case AlertData.NORMAL: 	executeCommand(scriptDir + normalLevelScript); 
										break;
				case AlertData.WARNING: executeCommand(scriptDir + warningLevelScript);
										break;
				case AlertData.ALERT: 	executeCommand(scriptDir + alertLevelScript);
										break;
				case AlertData.SHUTDOWN: 	executeCommand(scriptDir + shutdownLevelScript);
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
	
	
	public synchronized String executeCommand(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command,null,new File(scriptDir));
			/*
				p.waitFor();
						
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            	String line = "";			
				while ((line = reader.readLine())!= null) {
					output.append(line + "\n");
				}
			*/

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
