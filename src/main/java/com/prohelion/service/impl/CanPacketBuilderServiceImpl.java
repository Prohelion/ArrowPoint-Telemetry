package com.prohelion.service.impl;

import java.nio.ByteBuffer;
import org.apache.commons.codec.DecoderException;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

import com.prohelion.canbus.model.CanPacket;

@Service("canPacketBuilderService")
public class CanPacketBuilderServiceImpl {

    @ServiceActivator
    public CanPacket buildCanPacket(String[] csvLine) throws DecoderException {

        Integer id = Integer.parseInt(csvLine[2].trim().substring(2), 16);
        // Long data = Long.parseLong(csvLine[3].trim().substring(2), 16);
        byte[] theData = csvLine[4].trim().substring(2).getBytes();
        
        CanPacket cp = new CanPacket(ByteBuffer.allocate(4).putInt(id).array(), false, false, (byte)theData.length, theData);
               
        return cp;
    }
}
