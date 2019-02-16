package com.prohelion.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/")
public class LapTrackerController extends AbstractController {
    
    private static final Logger LOG = LoggerFactory.getLogger("LapTracker");
    
    Map<Integer,Long> lastLaps = new HashMap<Integer,Long>();

		
    public LapTrackerController() {
        
    }
    
        
    @RequestMapping(value = { "/laptracker.html" }, method = RequestMethod.GET)
    public String getLapTracker(Model model) {
        return "laptracker";
    }
    
    @RequestMapping(value = { "/laptracker.json" }, method = RequestMethod.GET, params = { "teamNumber" })
    public @ResponseBody String getLappedCar(@RequestParam(required = true) Integer teamNumber) {
    	
    	// Get the time for the last lap
    	Long lastLapTime = lastLaps.get(teamNumber);
    	Long currentTime = System.currentTimeMillis();
    	Long timeDeltaSec = (long)0;
    	    	
    	if (lastLapTime != null) {
    		timeDeltaSec  = (currentTime - lastLapTime) / 1000;
    		LOG.info(teamNumber.toString() + ", " + timeDeltaSec);	
    	} else
    		LOG.info(teamNumber.toString() + ",");
    	
    	lastLaps.put(teamNumber, currentTime);

    	if ( timeDeltaSec == 0) 
    		return "Last Recorded Team: " + teamNumber + ", First lap recorded so lap times yet";
    	else
    		return "Last Recorded Team: " + teamNumber + ", Lap time (sec): " + timeDeltaSec;    	
        
    }
    
   
}
