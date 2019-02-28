package com.prohelion.scheduled.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.prohelion.scheduled.Worker;
	 
/**
 * An asynchronous worker
 */
@Transactional
@Component("calculateRollingPowerAvgWorker")
public class CalculateRollingPowerAvg implements Worker {
	   
	
	 @PersistenceContext
	 private EntityManager em;
	
	 /**
	  * This method will be wrapped in a proxy so that the method is 
	  * actually invoked by a TaskExecutor instance
	  */
	@Async    
	public void work() {
				
		em.createNativeQuery("select count(calcRollingPowerAvg (600, 'sht_term_trend_data', 'avg_power', 16420, 16416))").getSingleResult();
		
		return;
	}

}	
	
	
