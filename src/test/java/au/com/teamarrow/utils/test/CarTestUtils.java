package au.com.teamarrow.utils.test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import au.com.teamarrow.canbus.model.CanPacket;
import au.com.teamarrow.canbus.model.UdpPacket;

/*
   ______________  _________
  / ____/ ____/  |/  / ____/
 / /   / /   / /|_/ / /     
/ /___/ /___/ /  / / /___   
\____/\____/_/  /_/\____/   

Cruise Control Mission Control v1.0
                            
 */

@Component
@ContextConfiguration(locations = {"/spring-test.xml"})
@Service
public class CarTestUtils {

	
	//	can bus globals
	@Autowired
    @Qualifier("canbusOutputChannel")
    MessageChannel mcInput;
	
    static CanPacket canPacket;
    static UdpPacket udpPacket = new UdpPacket();
    
    byte[] bytes = new byte[50];

    // Based on a tire diameter of 0.541m
    // Rolls 2 x Pi x r ms = 1.6996m
    // 100kph = 27.77 mps = 16.434 rps = 981 rpm
    // Therefore our multiple is 9.81
    // 100kph x 9.81 = 981 rpm 
    private static float kphtorpm = (float)9.81;
   
    public static double kphToRPM(double kph) {
    	return (double)kph * kphtorpm;
    }
    
    public static double rpmToKPH(double rpm) {
    	return (double)rpm / kphtorpm;
    }    
    
	public static byte[] hexStringToByteArray(String s) {
	     int len = s.length();
	     byte[] data = new byte[len / 2];
	     for (int i = 0; i < len; i += 2) {
	         data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                              + Character.digit(s.charAt(i+1), 16));                                    
	     }
	                   
	     return data;
	 }
	 
	 public void sendCan(CanPacket canPacket) {
		 
		 UdpPacket udpPacket = new UdpPacket();
		 ArrayList<CanPacket> canPacketList = new ArrayList<CanPacket>();
	 	 canPacketList.add(canPacket);
	 		
	 	 udpPacket.setCanPackets(canPacketList);
	 	 mcInput.send(MessageBuilder.withPayload(udpPacket).build());
		 
	 }
	
	
	 public void sendCan(String idStr, String byteStr ) {
	 	    	
	 		byte[] id = {0x00, 0x00, 0x00, 0x00};
	 		byte[] idIn = hexStringToByteArray(idStr);
	 		byte[] data = hexStringToByteArray(byteStr);
	 		byte[] invertData = new byte[8];
	 		
	 		// because idStr is only two bytes we need to pad it.
	 		id[2] = idIn[0];
	 		id[3] = idIn[1];
	 		
	 		CanPacket canPacket;
	 		    		    		    		    	   
	 		// Invert the data because our significant bit is opposite
	 		for (int i = 0; i<=data.length-1; i++)	
	 			invertData[i] = data[(data.length-1)-i];
	 		
	 		canPacket = new CanPacket(id, false, false, (byte)8, invertData);
	 		
	 		sendCan(canPacket);
	 	
	 }
	 
	 	    
    
    
	public void cruiseOff() {
		
		byte[] id = {0x00, 0x00, 0x05, 0x0A};
		byte[] data = new byte[8];
		byte[] invertData = new byte[8];
		    	  
		ByteBuffer bb = ByteBuffer.allocate(4);
		
	    bb.putInt(0); 
	    
	    data[0] = (byte)2;
	    
		System.arraycopy(bb.array(), 0, data, 4, bb.array().length);
	
		// Invert the data because our significant bit is opposite
		for (int i = 0; i<=7; i++)	invertData[i] = data[7-i];	               
		
		canPacket = new CanPacket(id, false, false, (byte)8, invertData);

		sendCan(canPacket);
		
		System.out.println("===== CRUISE CONTROL DISABLED =====");
	        	
	}
	
	public void sendPID(int p, int i, int d) {
		
		byte[] id = {0x00, 0x00, 0x05, 0x09};
		byte[] data = new byte[8];
		byte[] invertData = new byte[8];
		
		ByteBuffer bb = ByteBuffer.allocate(4);
	    bb.putInt(p);	    
	    System.arraycopy(bb.array(), 0, data, 4, bb.array().length);
	    
	    bb = ByteBuffer.allocate(4);
	    bb.putInt(i);
	    System.arraycopy(bb.array(), 0, data, 2, bb.array().length);
	    
	    bb = ByteBuffer.allocate(4);
	    bb.putInt(d);
	    System.arraycopy(bb.array(), 0, data, 0, bb.array().length);

	    // Invert the data because our significant bit is opposite
	 	for (int c = 0; c<=7; c++)	invertData[c] = data[7-c];
	 		
	 	canPacket = new CanPacket(id, false, false, (byte)8, invertData);

	 	sendCan(canPacket);
	 	
	}
	
	public void sendRPM(int rpm) {
	
		byte[] id = {0x00, 0x00, 0x05, 0x0A};
		byte[] data = new byte[8];
		byte[] invertData = new byte[8];
		
		ByteBuffer bb = ByteBuffer.allocate(4);
		
	    bb.putInt(rpm);
	    
	    data[0] = (byte)1;
	    
		System.arraycopy(bb.array(), 0, data, 4, bb.array().length);
	
		// Invert the data because our significant bit is opposite
		for (int i = 0; i<=7; i++)	invertData[i] = data[7-i];
		
		canPacket = new CanPacket(id, false, false, (byte)8, invertData);

		sendCan(canPacket);

	}
    
    
    public void runAtSpeed(int kph, int seconds, boolean prompt) {

    	byte[] id = {0x00, 0x00, 0x05, 0x0A};
    	byte[] data = new byte[8];
    	byte[] invertData = new byte[8];
    	byte[] bytes = new byte[50];
    	
    	String response;
    	    	  
    	if (prompt) {
    	
    		try {
	    		System.out.println("Setting speed to " + kph + "kph for " + seconds + " seconds.");
	        	System.out.println("type GO to activate");
	        	System.in.read(bytes);	        	
	        	response = new String(bytes); 
	        }
	        catch (IOException ex){
	        	System.out.println("AN ERROR HAS OCCURED TRYING TO GET THE PROMPT");
	        	response = new String("STOP");
	        }	        		    	      
	        	
    	} else response = new String("GO"); 
    	
    	
    	if (response.startsWith("GO")) {
    	
	    	ByteBuffer bb = ByteBuffer.allocate(4);
	    	
	    	Float rpm = kph * kphtorpm;
	    	
	        bb.putInt(rpm.intValue()); 
	        
	        data[0] = (byte)1;
	        
			System.arraycopy(bb.array(), 0, data, 4, bb.array().length);
	
			// Invert the data because our signifigant bit is opposite
			for (int i = 0; i<=7; i++)	invertData[i] = data[7-i];	               
	    	
	    	canPacket = new CanPacket(id, false, false, (byte)8, invertData);
	    			    
	    	System.out.println("Setting speed " + kph + "kph for " + seconds + " seconds");
	    	
	    	sendCan(canPacket);
	    	
	    	for (int i = 0; i < seconds; i++) {
	    
	            try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    	}
	    	
    	}
    }
    	
}
