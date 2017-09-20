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
public class GpsSplitter {
	
	private static final Logger LOG = LoggerFactory.getLogger(GpsSplitter.class);
		
	private Double safeValueOf(String input, Double defaultValue) {
			
		Double value = defaultValue;
		
		try {
			value = Double.valueOf(input);
		} catch (NumberFormatException ex) {}
		
		return value;
		
	}
	
	private Double GPRMC2Degrees(Double value) {

		LOG.error("Value = " + value); 
		
		int degrees = (int)(value / 100);
		
		double minutes = (value / 100) - degrees;
		
		double percentage = minutes / 0.6;
		
		double result = degrees + percentage;
		
		return result;
		
	}
	
	
	@Splitter
	public List<UdpPacket> splitGPS(byte[] bytes) {

		List<UdpPacket> udpPackets = new ArrayList<UdpPacket>();
		String decoded = null;		
		
		try {
			decoded = new String(bytes, "UTF-8");			
		} catch (UnsupportedEncodingException e) {
			LOG.error("Unable to decode Weather information");
			e.printStackTrace();
		}  // example for one encoding type			
			
		if (decoded != null) {	
			LOG.debug("Decoded GPS string = " + decoded);
			
			String[] gpsArray = decoded.split(",");
			
			ArrayList<CanPacket> canPacketList1 = new ArrayList<CanPacket>();
			ArrayList<CanPacket> canPacketList2 = new ArrayList<CanPacket>();
			UdpPacket udpPacket1 = new UdpPacket();
			UdpPacket udpPacket2 = new UdpPacket();
			
			if ( gpsArray[0].equals("$GPRMC") ) {
								
				Double latitude = safeValueOf(gpsArray[3],(double)0);
				Double longitude = safeValueOf(gpsArray[5],(double)0);
				Double speed = (Double)(safeValueOf(gpsArray[7],(double)0) * 0.514444444);
				Double direction = safeValueOf(gpsArray[8],(double)0);
				
				if (latitude < 1000) {
					LOG.error("Got a latitude issue");
					latitude = latitude * 10;
				}
				if (longitude < 10000) {
					LOG.error("Got a longitude issue");
					longitude = longitude * 10;
				}
								
				latitude = GPRMC2Degrees(latitude);
				longitude = GPRMC2Degrees(longitude);
				
				if (gpsArray[4].equals("S")) latitude = latitude * -1;
				if (gpsArray[6].equals("W")) longitude = longitude * -1;
								
				canPacketList1.add(new CanPacket(0x331,longitude.floatValue(),latitude.floatValue()));
				udpPacket1.setCanPackets(canPacketList1);
				udpPackets.add(udpPacket1);				
				
				canPacketList2.add(new CanPacket(0x332,direction.floatValue(),speed.floatValue()));								
				udpPacket2.setCanPackets(canPacketList2);
				udpPackets.add(udpPacket2);										
				
			}
			
			
			if ( gpsArray[0].equals("$GPGGA") ) {
				
				Double altitude = safeValueOf(gpsArray[9],(double)0);
				int numberOfSatelites = Integer.valueOf(gpsArray[7]);
								
				canPacketList1.add(new CanPacket(0x333,numberOfSatelites,altitude.floatValue()));				
				udpPacket1.setCanPackets(canPacketList1);
				udpPackets.add(udpPacket1);				
				
			}

			
		}

        return udpPackets;
    }
	
}



