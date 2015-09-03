package au.com.teamarrow.environment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Splitter;
import org.springframework.stereotype.Component;

import au.com.teamarrow.canbus.model.CanPacket;
import au.com.teamarrow.canbus.model.UdpPacket;

@Component
public class WeatherSplitter {
	
	private static final Logger LOG = LoggerFactory.getLogger(WeatherSplitter.class);
	
	@Splitter
	public List<UdpPacket> splitWeather(byte[] bytes) {

		List<UdpPacket> udpPackets = new ArrayList<UdpPacket>();
		Float tempInC = new Float(0);
		Float windChillInC = new Float(0);
		Float relativeHumidity = new Float(0);
		Float relativeAirPressure = new Float(0);
		Float windSpeed = new Float(0);
		Float windDirection = new Float(0);
		Float compassHeading = new Float(0);
		Float actualGlobalRadiation = new Float(0);
		Float avgGlobalRadiation = new Float(0);
		Float minGlobalRadiation = new Float(0);
		Float maxGlobalRadiation = new Float(0);
		Float airDensity = new Float(0);
		String decoded = null;		
		
		try {
			decoded = new String(bytes, "UTF-8");			
		} catch (UnsupportedEncodingException e) {
			LOG.error("Unable to decode Weather information");
			e.printStackTrace();
		}  // example for one encoding type			
			
		if (decoded != null) {
		
			LOG.debug("Decoded weather string = " + decoded);
			
			// Expected string should look something like this
			// E0;Ta+018.8C;Tp+011.3C;Tw+020.8C;Hr+061.7P;Pa+1025.5H;Sa+000.0M;Da+000.0D;Ra+00000.00M;Rt+000N;Ri+000.0M;
			String[] weatherArray = decoded.split(";");
			
			for (String weatherItem: weatherArray) {
				
				String key = weatherItem.substring(0, 2);
				Float value = new Float(0);
				CanPacket canPacket = null;
				ArrayList<CanPacket> canPacketList = new ArrayList<CanPacket>();
				UdpPacket udpPacket = new UdpPacket();
				
				// If for some reason this fails just set it to 0
				try {
					String valueStr = weatherItem.substring(2, weatherItem.length()-1); 
					value = Float.parseFloat(valueStr);
				}
				catch (Exception ex) {	}
								
				switch (key) {
					case "Ta": tempInC = value; break;						
					case "Tw": windChillInC = value; canPacket = new CanPacket(0x321,windChillInC,tempInC); break;
					case "Hr": relativeHumidity = value; break;
					case "Pa": relativeAirPressure = value; canPacket = new CanPacket(0x322,relativeHumidity,relativeAirPressure); break;
					case "Sa": windSpeed = value; break;
					case "Da": windDirection = value; canPacket = new CanPacket(0x323,windSpeed,windDirection); break;
										
					case "Ca": compassHeading = value; canPacket = new CanPacket(0x324,0,compassHeading); break;
					case "Ga": actualGlobalRadiation = value; break;
					case "Gg": avgGlobalRadiation = value; canPacket = new CanPacket(0x325,actualGlobalRadiation,avgGlobalRadiation); break;
					case "Gn": minGlobalRadiation = value; break;
					case "Gx": maxGlobalRadiation = value; canPacket = new CanPacket(0x326,minGlobalRadiation,maxGlobalRadiation); break;
					case "Ad": airDensity = value; canPacket = new CanPacket(0x327,0,airDensity); break;					
					
				}
				
				// Udp Packet can currently only cope with one in each list
				if (canPacket != null) {
					canPacketList.add(canPacket);				
					udpPacket.setCanPackets(canPacketList);
					udpPackets.add(udpPacket);
				}
			}			

		}

        return udpPackets;
    }
	
}


