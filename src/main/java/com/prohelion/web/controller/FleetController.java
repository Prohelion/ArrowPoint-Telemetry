package com.prohelion.web.controller;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prohelion.alerts.AlertManager;
import com.prohelion.dao.FleetMessage;
import com.prohelion.dao.ManualDataPoint;
import com.prohelion.dao.ManualLatLong;
import com.prohelion.maps.Route;
import com.prohelion.maps.impl.MapGenerator;
import com.prohelion.model.MeasurementData;
import com.prohelion.service.impl.FleetServiceImpl;

@Controller
@RequestMapping(value = "/")
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
    FleetServiceImpl fleetService;
	
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
			 	
		        OffsetDateTime dt = OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"));
		        length = 8;
	
		        floatValue = new Double(manualDataPoint.getFval());
		        integerValue = new Double(manualDataPoint.getFval()).intValue();
		        charValue = "";
			 	
			 	MeasurementData measurementData = new MeasurementData(dataPointCanId, dt, extended, retry,
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
		 		 		 
		 if (message != null && message.isEmpty() == false) fleetService.sendMessage("-d "+ receiver + ", " + message);
		 return "fleet";
	 }
	 
	
   
}


