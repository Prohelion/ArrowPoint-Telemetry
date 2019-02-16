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
@Component("archiveCanMediumTermWorker")
public class ArchiveCanMediumTermWorker implements Worker {
	   
	
	 @PersistenceContext
	 private EntityManager em;
	
	 /**
	  * This method will be wrapped in a proxy so that the method is 
	  * actually invoked by a TaskExecutor instance
	  */
	@Async    
	public void work() {
				
		em.createNativeQuery("select count(archiveCANData(300, 'sht_term_trend_data', 'med_term_trend_data'))").getSingleResult();
		
		return;
	}

}	
	
	

