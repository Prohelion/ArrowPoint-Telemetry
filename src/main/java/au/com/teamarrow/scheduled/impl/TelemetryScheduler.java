package au.com.teamarrow.scheduled.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import au.com.teamarrow.scheduled.Worker;
 
/**
 * Scheduler for handling jobs
 */
@Component
public class TelemetryScheduler {
 
    private static final Logger LOG = LoggerFactory.getLogger(TelemetryScheduler.class);
 
    @Autowired
    @Qualifier("archiveCanShortTermWorker")
    private Worker archiveCanShortTermWorker;

    @Autowired
    @Qualifier("archiveCanMediumTermWorker")
    private Worker archiveCanMediumTermWorker;

    @Autowired
    @Qualifier("archiveCanLongTermWorker")
    private Worker archiveCanLongTermWorker;

    @Autowired
    @Qualifier("calculateRollingPowerAvgWorker")
    private Worker calculateRollingPowerAvgWorker;
    
    @Autowired
    @Qualifier("alertsWorker")
    private Worker alertsWorker;
    
    /* Coders beware.... for here lies dragons */
    /* For some reason the annotations don't seem to work at least with the version of spring
     * that I have been using.  Hence I've gone back to the XML configuration for the time being
     */
        
	public void doArchiveCanShortTerm() {
		 LOG.debug("Start archive of short term data");	   
		 archiveCanShortTermWorker.work();	     
		 LOG.debug("Archive of short term data complete");
	 }
  
	public void doArchiveCanMediumTerm() {
		 LOG.debug("Start archive of medium term data");	   
		 archiveCanMediumTermWorker.work();	     
		 LOG.debug("Archive of medium term data complete");
    }
        
	public void doArchiveCanLongTerm() {
		 LOG.debug("Start archive of long term data");	   
		 archiveCanLongTermWorker.work();	     
		 LOG.debug("Archive of long term data complete");
    }
   	   
   	
	public void doExecuteCanRulesEngine() {
    	 LOG.debug("Start alerts worker");	   
		 alertsWorker.work();	     
		 LOG.debug("Alerts worker complete");
    }

}
