package au.com.teamarrow.service.impl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Splitter;
import org.springframework.stereotype.Component;

import au.com.teamarrow.canbus.model.CanPacket;
import au.com.teamarrow.dao.DataPointRepository;
import au.com.teamarrow.dao.DeviceRepository;
import au.com.teamarrow.dao.MeasurementRepository;
import au.com.teamarrow.model.DataPoint;
import au.com.teamarrow.model.Measurement;
import au.com.teamarrow.model.MeasurementData;

@Component
public class MeasurementDataSplitter {

private static final Logger LOG = LoggerFactory.getLogger(MeasurementDataSplitter.class);

    @Autowired
    private DeviceRepository deviceRepository;
    
    @Autowired
    private MeasurementRepository measurementRepository;
    
    @Autowired
    private DataPointRepository dataPointRepository;

    @Splitter
    public List<MeasurementData> extractMeasurementData(CanPacket canPacket) {
        LOG.debug("Extracting MeasurementData from canPacket");
        
        List<MeasurementData> measurements = new ArrayList<MeasurementData>();
                
        Measurement m = measurementRepository.findByCanId(canPacket.getIdBase10());
        
        LOG.debug("Looking for measurement for CP", canPacket.getIdBase10());
        
        List<DataPoint> points = dataPointRepository.findByMeasurement(m);
        
        if (points == null)
        {
        	LOG.debug("Found 0 points for measurement {}", canPacket.getIdBase10());
        	return measurements;
        }
        
        LOG.debug("Found {} points for measurement {}", points.size(), canPacket.getIdBase10());
        
        int cpId = Integer.parseInt(Hex.encodeHexString(canPacket.getId()), 16);
        byte[] data = canPacket.getData();
        
        for (DataPoint dp : points) {
            Integer dataLength = dp.getLength();
            Integer offset = dp.getDataOffsetPosition();
                        
            byte[] sa = ArrayUtils.subarray(data, offset, offset + dataLength);
            
            
            int bits = 0;
            for (int i = 0; i < sa.length; i++) {
                bits = bits | ((sa[i] & 0xFF) << (i * 8));
            }
            
            
            Float f = new Float(0.0);
            if (dp.getType().equalsIgnoreCase("float"))
            {
            	f = Float.intBitsToFloat(bits);
            	//f = new Float(ByteBuffer.wrap(sa).order(ByteOrder.LITTLE_ENDIAN).getFloat());
            	LOG.debug("A floating Point {} Length {} points offset {} data {} sa {} bits {} float {} ", dp.getDataPointCanId(), dataLength, offset, data, sa, bits, f);	
            }
            else
            {
            	f = new Float(bits);
            	LOG.debug("Non floating point of type {} Point {} Length {} points offset {} data {} sa {} bits {} float {} ", dp.getType(), dp.getDataPointCanId(), dataLength, offset, data, sa, bits, f);
            }
                 
            //LOG.warn("Point {} Length {} points offset {}", dp.getDataPointCanId(), dataLength, offset);
            measurements.add(new MeasurementData((cpId << 4) | offset, new DateTime(), canPacket.isExtended(), canPacket.isRtr(), 
                canPacket.getData().length, f.doubleValue(), f.intValue(), f.toString(), "Normal"));
        }
        
        return measurements;
    }
}
