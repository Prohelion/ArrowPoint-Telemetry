package au.com.teamarrow.canbus.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import au.com.teamarrow.utils.test.CarTestUtils;
import au.com.teamarrow.utils.test.PropertyAsserter;
import au.com.teamarrow.canbus.model.CanPacket;


public class CanPacketTest {

	
	
    @Test
    public void testfloatParameterConstructor() {
    	
    	// 0x501 = 1281
    	byte[] id = new byte[] { 0, 0 , 5, 1 };
    	
    	// TODO: this is a cludge, hexStringToByteArray should not be in the car utils class
    	byte[] data = CarTestUtils.hexStringToByteArray("3DF619D0457A0000");
    	   	
    	CanPacket cp = new CanPacket(1281,(float)0.120166,(float)4000);
    	
    	assertEquals(1281,cp.getIdBase10());
    	assertEquals(true,Arrays.equals(id, cp.getId()));
    	
    	
    	//assertEquals(true,Arrays.equals(data, cp.getData()));    	
    }
    
}

