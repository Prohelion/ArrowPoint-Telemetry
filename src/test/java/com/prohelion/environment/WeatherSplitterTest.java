package com.prohelion.environment;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.prohelion.canbus.model.UdpPacket;
import com.prohelion.environment.WeatherSplitter;

public class WeatherSplitterTest {

	@Test
	public void testUnpackingPackingE0() {
		
		String testInput = new String("Ta+018.8C;Tp+011.3C;Tw+020.8C;Hr+061.7P;Pa+1025.5H;Sa+000.0M;Da+000.0D;Ra+00000.00M;Rt+000N;Ri+000.0M;");
		
		byte[] weatherBytes = testInput.getBytes();
		
		WeatherSplitter weatherSplitter = new WeatherSplitter();
		
		List<UdpPacket> udpPackets = weatherSplitter.splitWeather(weatherBytes);
		
		assert(udpPackets != null);
		assertEquals(udpPackets.size(),3);				
		assertEquals(udpPackets.get(0).getCanPackets().get(0).getIdBase10(),0x321);		
	}
	
	
	@Test
	public void testUnpackingPackingE4() {
				
		String testInput = new String("Ca+040.0D;Ga+000.0W;Gn+000.0W;Gx+000.0W;Gg+000.0W;Ea+038.6J;Ba+013.9C;Ba+057.1F;Ad+001.2G;");
		
		byte[] weatherBytes = testInput.getBytes();
		
		WeatherSplitter weatherSplitter = new WeatherSplitter();
		
		List<UdpPacket> udpPackets = weatherSplitter.splitWeather(weatherBytes);
		
		assert(udpPackets != null);
		assertEquals(udpPackets.size(),4);	
	}
	
}
