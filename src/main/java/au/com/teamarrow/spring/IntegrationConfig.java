package au.com.teamarrow.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
@PropertySource({"classpath:application.properties"})
public class IntegrationConfig implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOG = LoggerFactory.getLogger(IntegrationConfig.class);
	
    @Autowired
    private Environment env;
    
    @Autowired
    @Qualifier("controlChannel")
    MessageChannel controlChannel;
    
    @Override public void onApplicationEvent(ContextRefreshedEvent event) {
    	    	
    	if (env.getProperty("enable.splunk.connector").equalsIgnoreCase("true")) {
    		LOG.info("Splunk Connector has been enabled");
    		controlChannel.send(new GenericMessage<String>("@splunkOutboundChannelAdapter.start()"));
    		controlChannel.send(new GenericMessage<String>("@measurementDataToSplunkTransformer.start()"));
    	} else
    		LOG.info("Splunk Connector has been disabled");
    	
    	if (env.getProperty("enable.weather.connector").equalsIgnoreCase("true")) {
    		LOG.info("Weather Connector has been enabled");
    		controlChannel.send(new GenericMessage<String>("@outbound-weather-poller.start()"));
    		controlChannel.send(new GenericMessage<String>("@inbound-weather-poll-replies.start()"));
    		controlChannel.send(new GenericMessage<String>("@triggerWeatherE0.start()"));
    		controlChannel.send(new GenericMessage<String>("@triggerWeatherE4.start()"));
    	} else
    		LOG.info("Weather Connector has been disabled");

    	if (env.getProperty("enable.gps.connector").equalsIgnoreCase("true")) {
    		LOG.info("GPS Connector has been enabled");
    		controlChannel.send(new GenericMessage<String>("@gatewayGps.start()"));
    	} else
    		LOG.info("GPS Connector has been disabled");
    	
    }
}


