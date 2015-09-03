package au.com.teamarrow.environment;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import au.com.teamarrow.canbus.model.CanPacket;
import au.com.teamarrow.canbus.model.UdpPacket;
import au.com.teamarrow.environment.GpsSplitter;

public class GpsSplitterTest {

	@Test
	public void testUnpackingPackingGPGGA() {
		
		String testInput = new String("$GPGGA,061543,2731.772,S,15257.760,E,1,8,0.9,10.9,M,46.9,M,0,2*5A");
		
		byte[] gpsBytes = testInput.getBytes();
		
		GpsSplitter gpsSplitter = new GpsSplitter();
		
		List<UdpPacket> udpPackets = gpsSplitter.splitGPS(gpsBytes);
		
		assert(udpPackets != null);
		assertEquals(udpPackets.size(),1);		
		
		CanPacket canPacket = udpPackets.get(0).getCanPackets().get(0); 
		
		assertEquals(canPacket.getIdBase10(),0x333);
		assertEquals((long)canPacket.getDataSegmentOne(),(long)8);
		assertEquals((long)canPacket.getDataSegmentTwo(),(long)10.9);
	}
	
	
	@Test
	public void testUnpackingPackingGPRMC() {
				
		String testInput = new String("$GPRMC,061650,A,2731.773,S,15257.756,E,10,145,250715,003.1,W*7F");
		
		byte[] gpsBytes = testInput.getBytes();
		
		GpsSplitter gpsSplitter = new GpsSplitter();
		
		List<UdpPacket> udpPackets = gpsSplitter.splitGPS(gpsBytes);
		
		assert(udpPackets != null);
		assertEquals(udpPackets.size(),2);				
		
		CanPacket canPacket331 = udpPackets.get(0).getCanPackets().get(0);
		CanPacket canPacket332 = udpPackets.get(1).getCanPackets().get(0);
		
		assertEquals(canPacket331.getIdBase10(),0x331);		
		assertEquals(canPacket332.getIdBase10(),0x332);
		
		assertEquals((long)canPacket331.getDataSegmentOne(),(long)15257.756);
		assertEquals((long)canPacket331.getDataSegmentTwo(),(long)-2731.773);
			
		assertEquals((long)canPacket332.getDataSegmentOne(),(long)(10 * 0.514444444));
		assertEquals((long)canPacket332.getDataSegmentTwo(),(long)145);
					
	
	}
	
}