package com.prohelion.service.impl;

import java.math.BigInteger;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Splitter;
import org.springframework.stereotype.Component;

import com.prohelion.canbus.model.CanPacket;
import com.prohelion.dao.DataPointRepository;
import com.prohelion.dao.MeasurementRepository;
import com.prohelion.model.DataPoint;
import com.prohelion.model.Measurement;
import com.prohelion.model.MeasurementData;

@Component
public class MeasurementDataSplitter {

	private static final Logger LOG = LoggerFactory.getLogger(MeasurementDataSplitter.class);
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    
    @Autowired
    private MeasurementRepository measurementRepository;
    
    @Autowired
    private DataPointRepository dataPointRepository;
    
    
    private String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

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
            
            // Now we reverse the array to get a Hex string that is correct as the byte order is wrong for
            // the utilities we are using for Integers
            ArrayUtils.reverse(sa);
            String hex = bytesToHex(sa);
            
            Float f = new Float(0.0);
            Integer i = new Integer(0);
            String s = new String("");
            
            if (dp.getType().equalsIgnoreCase("float"))
            {
            	try { 
            		f = Float.intBitsToFloat(bits);
            	} catch (Exception ex) {
            		LOG.error("A value CanID {} data {} could not be converted to a float", dp.getDataPointCanId(), data);
            		f = (float)0;
            	}
            	i = f.intValue();
            	s = f.toString();            	
            	LOG.debug("A floating Point {} Length {} points offset {} data {} sa {} bits {} float {} ", dp.getDataPointCanId(), dataLength, offset, data, sa, bits, f);	
            }
            else if (dp.getType().equalsIgnoreCase("int") || dp.getType().equalsIgnoreCase("uint"))
            {       
            	try {
            		//i = new Integer(Integer.valueOf(hex,16).intValue());
            		i = new BigInteger(hex, 16).intValue(); 
            	} catch (Exception ex) {
            		LOG.error("A value CanID {} data {} could not be converted to an int", dp.getDataPointCanId(), data);
            		f = (float)0;
            	}

            	f = new Float(i);
            	s = i.toString();            	            
            	LOG.debug("A integer Point {} Length {} points offset {} data {} sa {} bits {} float {} ", dp.getDataPointCanId(), dataLength, offset, data, sa, bits, f);	
            }            
            else            	
            {
            	try {
            		f = new Float(bits);
            	} catch (Exception ex) {
            		LOG.error("A value CanID {} data {} could not be converted to a float", dp.getDataPointCanId(), data);
            		f = (float)0;
            	}
            	
            	i = f.intValue();
            	s = f.toString();            
            	LOG.debug("Non floating point or integer type {} Point {} Length {} points offset {} data {} sa {} bits {} float {} ", dp.getType(), dp.getDataPointCanId(), dataLength, offset, data, sa, bits, f);
            }
                 
            //LOG.warn("Point {} Length {} points offset {}", dp.getDataPointCanId(), dataLength, offset);
            OffsetDateTime dt = OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"));
            measurements.add(new MeasurementData((cpId << 4) | offset, dt, canPacket.isExtended(), canPacket.isRtr(), 
                canPacket.getData().length, f.doubleValue(), i, s, "Normal"));
        }
        
        return measurements;
    }
}
