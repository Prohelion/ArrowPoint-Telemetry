package com.prohelion.service;

import java.util.List;

import org.springframework.messaging.Message;

import com.prohelion.model.LongTermTrendData;
import com.prohelion.model.MeasurementData;
import com.prohelion.model.MediumTermTrendData;
import com.prohelion.model.PowerUseDto;
import com.prohelion.model.ShortTermTrendData;


public interface MeasurementDataService {

    List<MeasurementData> getMeasurementsForDevice(Integer deviceId);
 
    void processMeasurementData(Message<MeasurementData> message);

    List<MeasurementData> findLatestDataForCanId(Integer canId);

    List<ShortTermTrendData> getShortTermTrendDataForDevice(Integer deviceId);

    List<MediumTermTrendData> getMediumTermTrendDataForDevice(Integer deviceId);

    List<LongTermTrendData> getLongTermTrendDataForDevice(Integer deviceId);
    
    List<PowerUseDto> getShortTermPowerData();
    
    List<PowerUseDto> getMediumTermPowerData();
    
    List<PowerUseDto> getLongTermPowerData();
}
