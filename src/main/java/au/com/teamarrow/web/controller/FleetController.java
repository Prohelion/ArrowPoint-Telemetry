package au.com.teamarrow.web.controller;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import au.com.teamarrow.alerts.AlertManager;
import au.com.teamarrow.dao.FleetMessage;
import au.com.teamarrow.dao.ManualDataPoint;
import au.com.teamarrow.dao.ManualLatLong;
import au.com.teamarrow.maps.Route;
import au.com.teamarrow.maps.impl.MapGenerator;
import au.com.teamarrow.model.MeasurementData;

@Controller
@RequestMapping(value = "/")
@PropertySource({"classpath:application.properties"})
public class FleetController extends AbstractController {
        
	private static final Logger LOG = LoggerFactory.getLogger(FleetController.class);
	
	@Autowired
	private MapGenerator mapGenerator;
	
	@Autowired
	private Route route;
	
	@Autowired
    @Qualifier("AlertManager")
    AlertManager alertManager;
	
	@Autowired
    @Qualifier("measurementAggregatedDataChannel")
    MessageChannel measurementAggregatedDataChannel;
	
	@Autowired
    @Qualifier("serverBytes2GpsSplitterChannel")
    MessageChannel serverBytes2GpsSplitterChannel;

	@Autowired
	private Environment env;
	
    private static final int MAX_UDP_DATAGRAM_LEN = 512;

	
    public FleetController() {
        
    }
        
    @RequestMapping(value = { "/fleet.html" }, method = RequestMethod.GET)
    public String getFleet(Model model) {
        return "fleet";
    }

    @RequestMapping(value = { "/alertsReset" }, method = RequestMethod.GET)
    public String getAlertsReset(Model model) {
    	alertManager.resetAlerts();
        return "fleet";
    }

    
	@RequestMapping(value="/maps/{type}", method = RequestMethod.GET, produces = "application/vnd.google-earth.kml+xml; charset=utf-8")
	public @ResponseBody String getMapInKml(@PathVariable String type) {			
		
		// Bit of a hack but this causes the route file to be reloaded
		route.setRouteFile(route.getRouteFile());
		mapGenerator.setRoute(route);
		
		return mapGenerator.generateAllLayers();
	}
    

	 @RequestMapping("/gps")
	 public String gpsAction(@ModelAttribute("gps") ManualLatLong manualLatLong, Map<String, Object> map, HttpServletRequest request) {

		 	int latDegrees = (int)manualLatLong.getLatitude();
		 	int longDegrees = (int)manualLatLong.getLongitude();
		 
		 	double latMinutes = manualLatLong.getLatitude() - latDegrees;
		 	double longMinutes = manualLatLong.getLongitude() - longDegrees;
		 
		 	latMinutes = .60 * latMinutes;
		 	longMinutes = .60 * longMinutes;
		 	
		 	double finalLat = (latDegrees + latMinutes) * 100;
		 	double finalLong = (longDegrees + longMinutes) * 100;
		 	
		 	String GPRMCStr = "$GPRMC,061650,A," + finalLat + ",S," + finalLong + ",E,10,145,250715,003.1,W*7F";
		 	
		 	serverBytes2GpsSplitterChannel.send(MessageBuilder.withPayload(GPRMCStr.getBytes()).build());
		 	
			return "fleet";
	 }


	 @RequestMapping("/dataPoint")
	 public String dataPointAction(@ModelAttribute("dataPoint") ManualDataPoint manualDataPoint, Map<String, Object> map, HttpServletRequest request) {
		 
		 	Integer dataPointCanId;
		 	DateTime timestamp;
	        Integer length;
	        Double floatValue;
	        Integer integerValue;
	        String charValue;
	        
	        String state = new String("Normal");
		 	Boolean extended = false;
		 	Boolean retry = false;

		 	try {
		 	
		        if (manualDataPoint.getDataPointOptions().equals("0")) 
		        	dataPointCanId = new Integer(manualDataPoint.getDataPoint());
		        else
		        	dataPointCanId = new Integer(manualDataPoint.getDataPointOptions());
			 	
		        timestamp = DateTime.now();
		        length = 8;
	
		        floatValue = new Double(manualDataPoint.getFval());
		        integerValue = new Double(manualDataPoint.getFval()).intValue();
		        charValue = "";
			 	
			 	MeasurementData measurementData = new MeasurementData(dataPointCanId, timestamp, extended, retry,
			 	        length, floatValue, integerValue, charValue, state);
			 					
				measurementAggregatedDataChannel.send(MessageBuilder.withPayload(measurementData).build());
		
		 	} catch (Exception ex) {
		 		LOG.error(ex.getMessage());
		 	}
		 	
			return "fleet";
	 }
	 
	 @RequestMapping("/message")
	 public String messageAction(@ModelAttribute("fleetMessage") FleetMessage fleetMessage, Map<String, Object> map, HttpServletRequest request) {
		 
		 String message = "Logical Error on web interface if you see this message"; 
		 String receiver = "Team";

		 switch (fleetMessage.getMessageReceiver()) {
         	case FleetMessage.RECEIVER_TEAM: receiver = "Team"; break;
         	case FleetMessage.RECEIVER_ARROW1: receiver = "Arrow1"; break;
         	case FleetMessage.RECEIVER_LEAD: receiver = "Lead"; break;
         	case FleetMessage.RECEIVER_CHASE: receiver = "Chase"; break;
		 }

		 switch (fleetMessage.getMessageOptions()) {
         	case FleetMessage.MESSAGE_TYPED: message = fleetMessage.getMessage(); break;
         	case FleetMessage.MESSAGE_COMMS_CHECK: message = "Comms check?"; break;
         	case FleetMessage.MESSAGE_LOST_COMMS: message = "Lost comms"; break;
         	case FleetMessage.MESSAGE_TARGET: message = "Target = "+ fleetMessage.getMessage(); break;
         	case FleetMessage.MESSAGE_BOXBOXBOX: message = "BOX BOX BOX"; break;
		 }	
		 
		 		 
		 if (message != null && message.isEmpty() == false) sendMessage("-d "+ receiver + ", " + message);
		 return "fleet";
	 }
	 
	 protected void sendMessage(String message) {
		 
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


