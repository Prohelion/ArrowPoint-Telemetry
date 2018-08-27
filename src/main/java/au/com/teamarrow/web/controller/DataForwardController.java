package au.com.teamarrow.web.controller;

import java.net.URI;
import java.util.Iterator;
import java.util.List;

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

import au.com.teamarrow.model.ShortTermTrendData;

@Controller
public class DataForwardController {
	
	@Autowired
    @Qualifier("measurementAggregatedDataChannel")
    MessageChannel measurementAggregatedDataChannel;	
	
	@RequestMapping(value = { "/forward-data.json" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> receiveForwardedData(@RequestBody List<ShortTermTrendData> forwardedData) {		
	
		try {
		
			Iterator<ShortTermTrendData> iterator = forwardedData.iterator();
		
			while (iterator.hasNext()) {
				ShortTermTrendData shortTermTrendData = iterator.next();			
				measurementAggregatedDataChannel.send(MessageBuilder.withPayload(shortTermTrendData).build());			
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
}
