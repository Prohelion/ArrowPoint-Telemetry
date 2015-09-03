package au.com.teamarrow.scheduled.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import au.com.teamarrow.scheduled.Worker;
 
/**
 * Scheduler for handling jobs
 */
@Service
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
    @Qualifier("pingRulesEngineWorker")
    private Worker pingRulesEngineWorker;
    
    @Autowired
    @Qualifier("canRulesEngineWorker")
    private Worker canRulesEngineWorker;    
    
    @Scheduled(cron="0 * * * * *")
	public void doArchiveCanShortTerm() {
		 LOG.debug("Start archive of short term data");	   
		 archiveCanShortTermWorker.work();	     
		 LOG.debug("Archive of short term data complete");
	 }
 
    @Scheduled(cron="0 0,10,20,30,40,50 * * * *")
	public void doArchiveCanMediumTerm() {
		 LOG.debug("Start archive of medium term data");	   
		 archiveCanMediumTermWorker.work();	     
		 LOG.debug("Archive of medium term data complete");
    }
        
    @Scheduled(cron="0 59 23 * * *")
	public void doArchiveCanLongTerm() {
		 LOG.debug("Start archive of long term data");	   
		 archiveCanLongTermWorker.work();	     
		 LOG.debug("Archive of long term data complete");
    }
   
	@Scheduled(cron="0 0,5,10,15,20,25,30,35,40,45,50,55 * * * *")
	public void doCalculateRollingPowerAvg() {
		 LOG.debug("Start calculation of rolling power averages");	   
		 calculateRollingPowerAvgWorker.work();	     
		 LOG.debug("Calculation of rolling power averages complete");
	}   
	
    @Scheduled(fixedDelay=30000)
	public void doExecutePingRulesEngine() {
		 /*LOG.debug("Starting Ping Rules Engine");	   
		 pingRulesEngineWorker.work();	     
		 LOG.debug("Ping Rules engine work complete");*/
	 }

    @Scheduled(fixedDelay=3000)
	public void doExecuteCanRulesEngine() {
		 /*LOG.debug("Starting Can Rules Engine");	   
		 canRulesEngineWorker.work();	     
		 LOG.debug("Can Rules engine work complete");*/
	 }

	

}
