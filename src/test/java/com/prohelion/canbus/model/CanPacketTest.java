package com.prohelion.canbus.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import org.junit.Test;

import com.prohelion.canbus.model.CanPacket;

public class CanPacketTest {

	
	
    @Test
    public void testfloatParameterConstructor() {
    	
    	// 0x501 = 1281
    	byte[] id = new byte[] { 0, 0 , 5, 1 };
    	
    	CanPacket cp = new CanPacket(1281,(float)0.120166,(float)4000);
    	
    	assertEquals(1281,cp.getIdBase10());
    	assertEquals(true,Arrays.equals(id, cp.getId()));
    }
    
}

