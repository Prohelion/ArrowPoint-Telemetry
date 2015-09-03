package au.com.teamarrow.service;

import java.util.List;

import org.springframework.messaging.Message;
import au.com.teamarrow.model.LongTermTrendData;
import au.com.teamarrow.model.MeasurementData;
import au.com.teamarrow.model.MediumTermTrendData;
import au.com.teamarrow.model.PowerUseDto;
import au.com.teamarrow.model.ShortTermTrendData;


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
