package au.com.teamarrow.scheduled.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import au.com.teamarrow.model.DataPoint;
import au.com.teamarrow.model.MeasurementData;
import au.com.teamarrow.scheduled.Worker;
import au.com.teamarrow.service.DataPointService;
import au.com.teamarrow.service.MeasurementDataService;

@Component("dataForwardWorker")
@PropertySource({"classpath:application.properties"})
public class DataForwardWorker implements Worker {
	
    private static final Logger LOG = LoggerFactory.getLogger(DataForwardWorker.class);


	@Autowired
	DataPointService dataPointService;
	
	@Autowired
	MeasurementDataService measurementDataService;
	
    @Autowired
    private Environment env;
	
	@Override
	public void work() {

		String forwardURL = env.getProperty("data.forward.url");
		if (forwardURL == null || forwardURL.length() == 0) {
			LOG.error("Forwarder has been enabled but no data.forward.url has been set");
			return;
		}			
		
		ArrayList<MeasurementData> measurementData = new ArrayList<MeasurementData>();		
		
		List<DataPoint> dataPoints = dataPointService.getDataPoints();
		
		Iterator<DataPoint> iterator = dataPoints.iterator();
		
		while (iterator.hasNext()) {			
	        CollectionUtils.addAll(measurementData, measurementDataService.findLatestDataForCanId(iterator.next().getDataPointCanId()));  			
		}
		
		if (measurementData != null && measurementData.size() > 0) {
		
			LOG.info("Forwarding " + measurementData.size() + " measurements to ");
			
			ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
			RestTemplate restTemplate = new RestTemplate(requestFactory);
			 
			HttpEntity<ArrayList<MeasurementData>> request = new HttpEntity<>(measurementData);
			URI location = restTemplate.postForLocation(forwardURL, request);
			
			if (location == null)
				LOG.error("Forwarder has been unable to forward to address (data.forward.url) = " + forwardURL );
			
		} else
			LOG.info("Data Forwarder is reporting no data available to forward");
		
	}
	
	private ClientHttpRequestFactory getClientHttpRequestFactory() {
	    int timeout = 5000;
	    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
	    clientHttpRequestFactory.setConnectTimeout(timeout);
	    return clientHttpRequestFactory;
	}

}
