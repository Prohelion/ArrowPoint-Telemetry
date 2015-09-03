package au.com.teamarrow.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.com.teamarrow.canbus.model.CanPacket;
import au.com.teamarrow.dao.MeasurementDataRepository;
import au.com.teamarrow.service.CanbusService;


@Service("canbusService")
@Transactional
public class CanbusServiceImpl implements CanbusService {
    
    private static final Logger LOG = LoggerFactory.getLogger(CanbusServiceImpl.class);
    
    @Autowired
    private MeasurementDataRepository measurementDataRepository;
    
    
    @ServiceActivator
    public void processUdpPacketMessage(Message<CanPacket> message) {
        CanPacket cp = message.getPayload();
        
        // Find the base ID by right and left shifting - each device has 32 bits (or 2^5)
        Integer cpBaseId = (cp.getIdBase10() >>> 5) << 5; 
        
        byte[] data = cp.getData();
        
        int intbits = (data[0] & 0xFF) 
            | ((data[1] & 0xFF) << 8) 
            | ((data[2] & 0xFF) << 16) 
            | ((data[3] & 0xFF) << 24);
        float firstFloat = Float.intBitsToFloat(intbits);
        
        intbits = (data[4] & 0xFF) 
            | ((data[5] & 0xFF) << 8) 
            | ((data[6] & 0xFF) << 16) 
            | ((data[7] & 0xFF) << 24);
        float second = Float.intBitsToFloat(intbits);
        
               
//        MeasurementData md = new MeasurementData(0, cp.getBaseId(), cp.getTimestamp(), cp.getId(), false, false, cp.getData().length, cp.getData(), (double)cp.getDataSegmentOne(), (double)cp.getDataSegmentTwo());
//        measurementDataRepository.save(md);

    }
}
