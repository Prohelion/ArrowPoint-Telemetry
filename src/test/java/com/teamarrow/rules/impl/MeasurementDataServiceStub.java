package com.teamarrow.rules.impl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.messaging.Message;

import au.com.teamarrow.model.LongTermTrendData;
import au.com.teamarrow.model.MeasurementData;
import au.com.teamarrow.model.MediumTermTrendData;
import au.com.teamarrow.model.PowerUseDto;
import au.com.teamarrow.model.ShortTermTrendData;
import au.com.teamarrow.service.MeasurementDataService;

public class MeasurementDataServiceStub implements MeasurementDataService {
	
	private List<MeasurementData> measurementDataList = new ArrayList<MeasurementData>();
	
	public MeasurementDataServiceStub() {
				
		java.util.Date date = new java.util.Date();
		DateTime dateTime = new DateTime(date.getTime());
				
		MeasurementData measurementData = new MeasurementData(1808, dateTime, false, false, 8, (double) 60, 60, "", "");
		
		measurementDataList.add(measurementData);	
	}

	@Override
	public List<MeasurementData> getMeasurementsForDevice(Integer deviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processMeasurementData(Message<MeasurementData> message) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<MeasurementData> findLatestDataForCanId(Integer canId) {
		// TODO Auto-generated method stub
	
		
		return measurementDataList;
	}

	@Override
	public List<ShortTermTrendData> getShortTermTrendDataForDevice(Integer deviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MediumTermTrendData> getMediumTermTrendDataForDevice(Integer deviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LongTermTrendData> getLongTermTrendDataForDevice(Integer deviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PowerUseDto> getShortTermPowerData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PowerUseDto> getMediumTermPowerData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PowerUseDto> getLongTermPowerData() {
		// TODO Auto-generated method stub
		return null;
	}

}
