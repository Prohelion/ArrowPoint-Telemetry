package au.com.teamarrow.rules.impl;

import java.io.IOException;
import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.teamarrow.model.MeasurementData;
import au.com.teamarrow.rules.Rule;

public class PingComponent implements Rule {

	String ruleName;
	String ip;
	String failureMsg; 
	String successMsg;
	String currentMsg = null;
	boolean isFailed = false;
	boolean wasFailed = false;	
	
    private static final Logger LOG = LoggerFactory.getLogger(PingComponent.class);
	
	public PingComponent(String ruleName, String ip, String failureMsg, String successMsg) {
		this.ruleName = ruleName;
		this.ip = ip;
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
	
	
	static String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
	private static boolean ping(String host) throws IOException, InterruptedException {
	    boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");

	    ProcessBuilder processBuilder = new ProcessBuilder("ping", isWindows? "-n" : "-c", "1", host);
	    Process proc = processBuilder.start();

	    int returnVal = proc.waitFor();
	    
	    if (returnVal != 0) return false;
	    
	    // Just because it succeeds, doens't mean that it worked in Windows as Windows returns a 0 return
	    // code in some failure situations
	    
	    String output = convertStreamToString(proc.getInputStream());
	    
	    if (output.contains("TTL")) 
	    	return true; 
	    else   	    
	    	return false;
	}

	@Override
	public void execute() {

		LOG.debug("Rule: [" + ruleName + "] Attempting to contact IP: " + ip);
 
        try
        {
            boolean status = ping(ip); 

        	wasFailed = isFailed;
            
            if (status)
            {
            	// It worked so report ok
                isFailed = false;
            }
	            else
            {
	            // Nope didn't work
	            isFailed = true;
            }
        }
        catch (Exception e)
        {
        	isFailed = true;            
        }
        
        if (isFailed) currentMsg = failureMsg; else currentMsg = successMsg;
        
	}

	@Override
	public Integer getCanId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMeasurementData(MeasurementData measurementData) {
		// TODO Auto-generated method stub
		
	}

}
