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
	
	private float safeValueOf(String input, float defaultValue) {
			
		float value = defaultValue;
		
		try {
			value = Float.valueOf(input);
		} catch (NumberFormatException ex) {}
		
		return value;
		
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
				
				float latitude = safeValueOf(gpsArray[3],0);
				float longitude = safeValueOf(gpsArray[5],0);
				float speed = (float)(safeValueOf(gpsArray[7],0) * 0.514444444);
				float direction = safeValueOf(gpsArray[8],0);
				
				if (gpsArray[4].equals("S")) latitude = latitude * -1;
				if (gpsArray[6].equals("W")) longitude = longitude * -1;
								
				canPacketList1.add(new CanPacket(0x331,latitude,longitude));
				udpPacket1.setCanPackets(canPacketList1);
				udpPackets.add(udpPacket1);				
				
				canPacketList2.add(new CanPacket(0x332,direction,speed));								
				udpPacket2.setCanPackets(canPacketList2);
				udpPackets.add(udpPacket2);										
				
			}
			
			
			if ( gpsArray[0].equals("$GPGGA") ) {
				
				float altitude = safeValueOf(gpsArray[9],0);
				int numberOfSatelites = Integer.valueOf(gpsArray[7]);
								
				canPacketList1.add(new CanPacket(0x333,altitude,numberOfSatelites));				
				udpPacket1.setCanPackets(canPacketList1);
				udpPackets.add(udpPacket1);				
				
			}

			
		}

        return udpPackets;
    }
	
}



