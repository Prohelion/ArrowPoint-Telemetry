package au.com.teamarrow.web.controller;

import java.net.URI;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import au.com.teamarrow.canbus.model.CarData;
import au.com.teamarrow.model.MeasurementData;

@Controller
public class DataForwardController {
	
	@Autowired
    @Qualifier("measurementAggregatedDataChannel")
    MessageChannel measurementAggregatedDataChannel;	
	
	@RequestMapping(value = { "/forward-data.json" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> receiveForwardedData(@RequestBody List<MeasurementData> forwardedData) {		
	
		try {
		
			Iterator<MeasurementData> iterator = forwardedData.iterator();
		
			while (iterator.hasNext()) {
				// Using new here to create a clone with a new ID
				MeasurementData measurementData = new MeasurementData(iterator.next());			
				measurementAggregatedDataChannel.send(MessageBuilder.withPayload(measurementData).build());			
			}
			
			URI location = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/")
					.buildAndExpand()
					.toUri();

			return ResponseEntity.created(location).build();				       
			
		}catch(Exception ex){
	    
			String errorMessage;
	        errorMessage = ex + " <== error";
	        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	        
	    }		
	}
	
	private void sendMessage(Integer dataPointCanId, Double floatValue) {

	 	DateTime timestamp = DateTime.now();;
        Integer length = 8;
        Integer integerValue = new Double(floatValue).intValue();;
        String charValue = "";
		String state = new String("Normal");
	 	Boolean extended = false;
	 	Boolean retry = false;
	 	
		MeasurementData measurementData = new MeasurementData(dataPointCanId, timestamp, extended, retry,
		        length, floatValue, integerValue, charValue, state);
		
		measurementAggregatedDataChannel.send(MessageBuilder.withPayload(measurementData).build());			
	}
	
	
	@RequestMapping(value = { "/car-data.json" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> receiveForwardedData(@RequestBody CarData carData) {	
		
		// Not required as it is already calculated as part of enrichment
		//sendMessage(Integer.parseInt("3450", 16),carData.getLastBusPower());
		
		sendMessage(Integer.parseInt("4024", 16),carData.getLastBusAmps());
		sendMessage(Integer.parseInt("4020", 16),carData.getLastBusVolts());			
		sendMessage(Integer.parseInt("4034", 16),carData.getLastSpeed() / 3.6);
		sendMessage(Integer.parseInt("6F44", 16),carData.getLastSOC());
		sendMessage(Integer.parseInt("5014", 16),new Double(carData.getLastMotorPowerSetpoint()));
		
		sendMessage(Integer.parseInt("7010", 16),carData.getLastArray1Volts());
		sendMessage(Integer.parseInt("7014", 16),carData.getLastArray1Amps());
		
		sendMessage(Integer.parseInt("7050", 16),carData.getLastArray2Volts());
		sendMessage(Integer.parseInt("7054", 16),carData.getLastArray2Amps());
		
		sendMessage(Integer.parseInt("7090", 16),carData.getLastArray3Volts());
		sendMessage(Integer.parseInt("7094", 16),carData.getLastArray3Amps());
		
		sendMessage(Integer.parseInt("6FA0", 16),carData.getLastBatteryVolts());
		
		sendMessage(Integer.parseInt("40B0", 16),carData.getLastMotorTemp());
		sendMessage(Integer.parseInt("40B4", 16),carData.getLastControllerTemp());
		
		sendMessage(Integer.parseInt("6F80", 16),new Double(carData.getLastMinimumCellV()));
		sendMessage(Integer.parseInt("6F92", 16),new Double(carData.getLastMaxCellTemp()));			
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/")
				.buildAndExpand()
				.toUri();

		return ResponseEntity.created(location).build();		
		
	}
	
}
