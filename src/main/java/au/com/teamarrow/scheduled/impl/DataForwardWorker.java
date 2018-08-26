package au.com.teamarrow.scheduled.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.teamarrow.model.DataPoint;
import au.com.teamarrow.model.MeasurementData;
import au.com.teamarrow.scheduled.Worker;
import au.com.teamarrow.service.DataPointService;
import au.com.teamarrow.service.MeasurementDataService;

@Component("dataForwardWorker")
public class DataForwardWorker implements Worker {

	@Autowired
	DataPointService dataPointService;
	
	@Autowired
	MeasurementDataService measurementDataService;
	
	@Override
	public void work() {

		ArrayList<MeasurementData> measurmentData = new ArrayList<MeasurementData>();
		
		List<DataPoint> dataPoints = dataPointService.getDataPoints();
		
		Iterator<DataPoint> iterator = dataPoints.iterator();
		
		while (iterator.hasNext()) {			
	        CollectionUtils.addAll(measurmentData, measurementDataService.findLatestDataForCanId(iterator.next().getDataPointCanId()));  			
		}
	}

}
