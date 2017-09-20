package au.com.teamarrow.canbus.serial;

import java.io.IOException;
import java.io.OutputStream;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.serializer.Serializer;
import org.springframework.stereotype.Component;

import au.com.teamarrow.canbus.model.UdpPacket;


@Component
public class UdpPacketSerializer implements Serializer<UdpPacket> {

	private byte[] mac;
	
	public String searchForMac() throws SocketException {
	    String firstInterface = null;        
	    Map<String, String> addressByNetwork = new HashMap<>();
	    Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

	    while(networkInterfaces.hasMoreElements()){
	        NetworkInterface network = networkInterfaces.nextElement();

	        byte[] bmac = network.getHardwareAddress();
	        if(bmac != null){
	            StringBuilder sb = new StringBuilder();
	            for (int i = 0; i < bmac.length; i++){
	                sb.append(String.format("%02X%s", bmac[i], (i < bmac.length - 1) ? "-" : ""));        
	            }

	            if(sb.toString().isEmpty()==false){
	                addressByNetwork.put(network.getName(), sb.toString());	                
	            }

	            if(sb.toString().isEmpty()==false && firstInterface == null){
	                firstInterface = network.getName();
	            }
	        }
	    }

	    if(firstInterface != null){
	        return addressByNetwork.get(firstInterface);
	    }

	    return null;
	}
	
	
	
	@Override
	public void serialize(UdpPacket udp, OutputStream arg1) throws IOException {
		// Origional payload :        { 0x00, 0x54, 0x72, 0x69, 0x74, 0x69, 0x75, 0x6d, 0x00, 0x00, (byte)0xc5, (byte)0xc0, (byte)0xcf, (byte)0xc2, 0x50, 0x00, 0x00, 0x00, 0x05, 0x00, 0x00, 0x08, 0x01, 0x00, 0x00, 0x00, 0x36, 0x38, 0x30, 0x54 };
		byte[] outputBytes = new byte[30]; 
		byte[] header = new byte[]        { 0x00, 0x54, 0x72, 0x69, 0x74, 0x69, 0x75, 0x6d, 0x00 };
		byte[] id = udp.getCanPackets().get(0).getId();
		byte[] data = udp.getCanPackets().get(0).getData();
					
        if (mac == null) {
        	mac = searchForMac().getBytes();
        }
		
		System.arraycopy(header, 0, outputBytes, 0, header.length);
		System.arraycopy(mac, 0, outputBytes, header.length + 1, mac.length);
		System.arraycopy(id, 0, outputBytes, 16, id.length);
		
		// Set the length of the data to 8
		outputBytes[21] = (byte)udp.getCanPackets().get(0).getLength();
		
		System.arraycopy(data, 0, outputBytes, 22, data.length);
		
		arg1.write(outputBytes);
		
	}

}
