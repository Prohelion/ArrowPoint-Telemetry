package com.prohelion.scheduled.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.prohelion.scheduled.Worker;
 
/**
 * Scheduler for handling jobs
 */
@Component
@PropertySource({"classpath:application.properties"})
public class TelemetryScheduler {
 	
    private static final Logger LOG = LoggerFactory.getLogger(TelemetryScheduler.class);
 
    @Autowired
    private Environment env;
        
    @Autowired
    @Qualifier("alertsWorker")
    private Worker alertsWorker;
   
    @Autowired
    @Qualifier("dataForwardWorker")
    private Worker dataForwardWorker;               
    
    @Scheduled(cron = "${data.forward.cron}")
	public void doDataForward() {
    	if (env.getProperty("enable.data.forward","false").equalsIgnoreCase("true")) {
    		LOG.debug("Start data forward worker");	   
    		dataForwardWorker.work();	     
    		LOG.debug("Data forward complete");
    	}
    }
    
	@Scheduled(fixedRate = 2000)
	public void doExecuteCanRulesEngine() {
		 if (env.getProperty("enable.alerts","false").equalsIgnoreCase("true")) {
			 //TODO: This should be more granular and enable by if we want scripts running or not
			 LOG.debug("Start alerts worker");	   
			 alertsWorker.work();	     
			 LOG.debug("Alerts worker complete");
		 }
    }

}
