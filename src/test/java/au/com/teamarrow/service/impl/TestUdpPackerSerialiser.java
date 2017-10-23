package au.com.teamarrow.service.impl;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.com.teamarrow.canbus.model.CanPacket;
import au.com.teamarrow.canbus.model.UdpPacket;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class TestUdpPackerSerialiser {

	    @Autowired
	    @Qualifier("canbusOutputChannel")
	    MessageChannel mcInput;
	    
	    byte[] a = {};
	    byte[] b = {};
	    byte[] c = {};
	    
	    CanPacket canPacket;
	    UdpPacket udpPacket = new UdpPacket((byte)1, a, b, c );
	    	    
	    @Test
	    public void test() throws Exception {
	    
	    	byte[] id = {0x00, 0x00, 0x05, 0x0A};
	    	byte[] data = new byte[8];
	    	byte[] invertData = new byte[8];
	    	
	    	ByteBuffer bb = ByteBuffer.allocate(4); 
	        bb.putInt(200); 
	        
	        data[0] = (byte)1;
	        
			System.arraycopy(bb.array(), 0, data, 4, bb.array().length);

			for (int i = 0; i<=7; i++)	invertData[i] = data[7-i];	               
	    	
	    	canPacket = new CanPacket(id, false, false, (byte)8, invertData);
	    	
	    	ArrayList<CanPacket> canPacketList = new ArrayList<CanPacket>();
	    	canPacketList.add(canPacket);
	    	
	    	udpPacket.setCanPackets(canPacketList);
	    	
	    	// If this is failing on your machine run
	    	// netsh interface teredo set state disabled
	    	// as administrator
	    	
	        mcInput.send(MessageBuilder.withPayload(udpPacket).build()); 
	    }
	    
	}
	
	
