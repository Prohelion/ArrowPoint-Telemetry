package au.com.teamarrow.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.com.teamarrow.dao.DeviceRepository;
import au.com.teamarrow.dao.MeasurementRepository;
import au.com.teamarrow.model.Device;
import au.com.teamarrow.model.Measurement;
import au.com.teamarrow.service.MeasurementService;

@Service
public class MeasurementServiceImpl implements MeasurementService {

    @Autowired
    private DeviceRepository deviceRepository;
    
    @Autowired
    private MeasurementRepository measurementRepository;
    
    @Override
    @Transactional(readOnly = true)
    public Measurement get(Long measurementId) {
        return measurementRepository.findById(measurementId).orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Measurement> getMeasurementsForDevice(Long deviceId) {
        Device d = deviceRepository.findById(deviceId).orElse(null);
        
        List<Measurement> l = new ArrayList<Measurement>();
        l.addAll(d.getMeasurements());
        Collections.sort(l, new Comparator<Measurement>() {

            @Override
            public int compare(Measurement o1, Measurement o2) {
                return o1.getCanId().compareTo(o2.getCanId());
            }
        });
        
        return l;
    }
}
