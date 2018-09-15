package au.com.teamarrow.service.impl;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import au.com.teamarrow.service.FleetService;

@Service
@PropertySource({"classpath:application.properties"})
public class FleetServiceImpl implements FleetService {
	
	 private static final int MAX_UDP_DATAGRAM_LEN = 512;
	 
	 @Autowired
	 private Environment env;
	
	 public void sendMessage(String message) {
		 
			byte[] send_bytes = new byte[MAX_UDP_DATAGRAM_LEN];

	        InetAddress address = null;
	        try {
	            address = InetAddress.getByName(env.getProperty("udp.host"));
	        } catch (UnknownHostException e1) {
	            e1.printStackTrace();
	        }
			        
	        try {
	        	MulticastSocket socket = new MulticastSocket(new Integer(env.getProperty("udp.port"))); // must bind receive side
	        	socket.joinGroup(address);
	        	socket.setSoTimeout(100);
				
	            send_bytes = new String(message).getBytes();
	            		
	            DatagramPacket send_packet = new DatagramPacket(send_bytes, send_bytes.length, address, new Integer(env.getProperty("udp.port")));
	            socket.send(send_packet);
	            socket.close();
	            
	        } catch (Exception ex) {
	        	
	        }

	 }

}
