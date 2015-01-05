package au.com.teamarrow.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import au.com.teamarrow.model.Measurement;

@Transactional
public interface MeasurementService {
    
    Measurement get(Long measurementId);

    List<Measurement> getMeasurementsForDevice(Long deviceId);
}
