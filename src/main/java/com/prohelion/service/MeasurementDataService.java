package com.prohelion.service;

import java.util.List;
import org.springframework.messaging.Message;
import com.prohelion.model.MeasurementData;

public interface MeasurementDataService {

    List<MeasurementData> getMeasurementsForDevice(Integer deviceId);
 
    void processMeasurementData(Message<MeasurementData> message);

    List<MeasurementData> findLatestDataForCanId(Integer canId);
    
}
