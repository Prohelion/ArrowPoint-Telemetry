package au.com.teamarrow.scheduled.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import au.com.teamarrow.scheduled.Worker;
	 
/**
 * An asynchronous worker
 */
@Transactional
@Component("archiveCanShortTermWorker")
public class ArchiveCanShortTermWorker implements Worker {
	   
	
	 @PersistenceContext
	 private EntityManager em;
	
	 /**
	  * This method will be wrapped in a proxy so that the method is 
	  * actually invoked by a TaskExecutor instance
	  */
	@Async    
	public void work() {
				
		em.createNativeQuery("select count(archiveCANData(60, 'msrmnt_data', 'sht_term_trend_data'))").getSingleResult();
		
		return;
	}

}	
	
	
