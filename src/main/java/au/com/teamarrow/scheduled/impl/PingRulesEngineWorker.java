package au.com.teamarrow.scheduled.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import au.com.teamarrow.rules.Rule;
import au.com.teamarrow.rules.impl.PingComponent;
import au.com.teamarrow.scheduled.Worker;
	 
/**
 * An asynchronous worker
 */
@Transactional
@Component("pingRulesEngineWorker")
public class PingRulesEngineWorker implements Worker {
	 
	private List<Rule> rules;
	
	private static final Logger LOG = LoggerFactory.getLogger("Alerts");
	
	public PingRulesEngineWorker() {
	
		//todo: Do this via a configuration file
		rules = new ArrayList<Rule>();
	
		/*rules.add(new PingComponent("Arrow1 WCAN", "192.168.1.10", "Arrow1 WCAN is not available", "Arrow1 WCAN is active"));		
		rules.add(new PingComponent("Access Point (Solar Car)", "192.168.1.22", "Access Point (Solar Car) is not available", "Access Point (Solar Car) is active"));
		rules.add(new PingComponent("Network Link (Rear Escort)", "192.168.1.23", "Network Link (Rear Escort) is not available", "Network Link (Rear Escort) is active"));
		rules.add(new PingComponent("Access Point (Rear Escort)", "192.168.1.24", "Access Point (Rear Escort) is not available", "Access Point (Rear Escort) is active"));
		rules.add(new PingComponent("Serial to Ethernet (GPS)", "192.168.1.41", "Serial to Ethernet (GPS) is not available", "Serial to Ethernet (GPS) is active"));
		rules.add(new PingComponent("Serial to Ethernet (Weather)", "192.168.1.42", "Serial to Ethernet (Weather) is not available", "Serial to Ethernet (Weather) is active"));
		rules.add(new PingComponent("Telemetry", "192.168.1.60", "Telemetry is not available", "Telemetry is active"));
		rules.add(new PingComponent("Tablet 1", "192.168.1.101", "Tablet 1 is not available", "Tablet 1 is active"));
		rules.add(new PingComponent("Tablet 2", "192.168.1.102", "Tablet 2 is not available", "Tablet 2 is active"));
		rules.add(new PingComponent("Tablet 3", "192.168.1.103", "Tablet 3 is not available", "Tablet 3 is active"));
		rules.add(new PingComponent("Tablet 4", "192.168.1.104", "Tablet 4 is not available", "Tablet 4 is active"));
		rules.add(new PingComponent("Switch DHCP (Rear Escort)", "192.168.1.253", "Switch DHCP (Rear Escort) is not available", "Switch DHCP (Rear Escort) is active"));
		*/
	}
	
	public List<Rule> getRules() {
		return rules;
	}
	
	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	
	 @Async    
	public void work() {
					
		if (rules != null) 
			for (int i = 0; i < rules.size(); i++) {
				
				Rule rule = this.rules.get(i);
				
				rule.execute();
				
				if (rule.isStateChanged()) {
					if (rule.isFailed()) 
						LOG.warn(rule.getMessage());
					else
						LOG.info(rule.getMessage());
				}				
				
			}		 
		 
		return;
	}

}	
	
	

