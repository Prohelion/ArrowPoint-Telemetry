package au.com.teamarrow.maps.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.teamarrow.maps.Route;
import au.com.teamarrow.service.MeasurementDataService;
import au.com.teamarrow.service.impl.MeasurementDataEnrichment;
import au.com.teamarrow.maps.SimulationData;
import au.com.teamarrow.maps.VisualRender;

@Component
public class MapGenerator {

	@Autowired
    private MeasurementDataEnrichment enricherBean;
	
	private VisualRender visualRender;
	private SimulationData simulationData;
	
	private Route route;
	
	public MapGenerator () {
		visualRender = new KmlVisualRenderImpl();
		simulationData = new SimulationDataImpl();
	}
	
	public void setRoute(Route route) {
		this.route = route;
	}
	
	public void setMeasurementDataEnrichment(MeasurementDataEnrichment measurementDataEnrichment) {
		this.enricherBean = measurementDataEnrichment;
	}
	
	
	
	private void moveForward(SimulationData data, int currentSector) {

		try {

			// Go the distance (D = Speed * Time)
			route.goForwards(data.getActualSpeed() * data.getSimulationInterval());

			// What exactly is a sector? NOTE: Could be an interval of time? 
			if (route.getCurrentSector() > currentSector) {
				currentSector = route.getCurrentSector();
			}

			// Draws a line that represents the route
			visualRender.addToPath(route.getCurrentLatitude(), route.getCurrentLongitude(), route.getCurrentElevation());

		} catch (NoRouteNodeException e) {					
			e.printStackTrace();
		}


	}

	
	public void runRouteFromNodeToNode(int startNode, int endNode, Double speedMps, int minutesAhead, boolean showMarkers, boolean flyover) {
		
		try {
			route.setStartNode(startNode);
		} catch (NoRouteNodeException e) {		
			e.printStackTrace();
			return;
		}
				
		runRoute(startNode,endNode,route.getCurrentLatitude(),route.getCurrentLongitude(),speedMps,minutesAhead,showMarkers,flyover);		
	}
	
	public void runRouteFromStart(Double speedMps, int minutesAhead, boolean showMarkers, boolean flyover) {
		
		try {
			route.setStartNode(0);
		} catch (NoRouteNodeException e) {		
			e.printStackTrace();
			return;
		}
				
		runRoute(0,-1,route.getCurrentLatitude(),route.getCurrentLongitude(),speedMps,minutesAhead,showMarkers,flyover);
	}
	
	public void runRouteFromLatLong(Double startLat, Double startLong, double speedMps, int minutesAhead, boolean showMarkers, boolean flyover) {
		
		int currentSector = 0;
		
		try {
			route.gotoNearestNode(startLat, startLong);
		} catch (NoRouteNodeException e) {
			e.printStackTrace();
			return;
		}
		currentSector = route.getCurrentSector();
		
		runRoute(currentSector, -1, startLat, startLong, speedMps, minutesAhead, showMarkers,flyover);
				
	}
	
	
	private void runRoute(int currentSector, int toSector, Double startLat, Double startLong, Double speedMps, int minutesAhead, boolean showMarkers, boolean flyover)  {		

		int loopCount = 0;		
		int interval = 10;
		int outputInterval = 6;
		long intervalsCounted = 0;
		long secondsAhead = minutesAhead * 60;
		
		if (toSector <= 0) toSector = route.getNumberOfNodes() + 100;
		
		simulationData.setEnvironment(new EnvironmentImpl());
		simulationData.getEnvironment().setLatitude(startLat);
		simulationData.getEnvironment().setLatitude(startLong);
		simulationData.getEnvironment().setGregorianCalendar(new GregorianCalendar());

		// Set the actual speed to our target speed 
		simulationData.setActualSpeed(speedMps);		
		simulationData.setSimulationInterval(interval);
		
		if (showMarkers == true) {
			addPlacemark("ff00ff55");			
		}
		
		while (route.isRouteComplete() == false && (minutesAhead == -1 || intervalsCounted <= secondsAhead) && route.getCurrentSector() < toSector) {

				// Add one second to the calendar
				simulationData.getEnvironment().getGregorianCalendar().add(Calendar.SECOND, simulationData.getSimulationInterval());

				// We're not at a stop so keep moving forward
				moveForward(simulationData, currentSector);
			
				// Set the longitude and latitude of the environment 
				setLongLat(simulationData, route.getCurrentLatitude(), route.getCurrentLongitude());
				
				if (showMarkers == true && loopCount >= outputInterval) {
					addPlacemark("ff00ff55");
					loopCount = 0;
				}
				
				if (flyover == true) {
					visualRender.addToTour(route.getCurrentLatitude(), route.getCurrentLongitude(),route.getCurrentElevation()); 
				}
				
				loopCount++;
				intervalsCounted = intervalsCounted + interval;
		}

 
	}

	
	private void addPlacemark(String color) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

		String message = new String(dateFormat.format(simulationData.getEnvironment().getGregorianCalendar().getTime()));
		
		visualRender.addPlacemark(route.getCurrentLatitude(), route.getCurrentLongitude(),
				route.getCurrentElevation(),message , color);

	}
	
	
	private void setLongLat(SimulationData data, double currentLatitude, double currentLongitude) {

		data.getEnvironment().setLatitude(route.getCurrentLatitude());
		data.getEnvironment().setLongitude(route.getCurrentLongitude());

	}


	
	public String generateRouteMap() {
		// First generate a full run of the race
		runRouteFromStart(100/3.6,-1,false,false);
		
		return visualRender.toString();
	}
	
	
	public String generateCurrentLatLong(Double startLat, Double startLong) {
		
		visualRender.addPlacemark(startLat, startLong ,0, "Solar Car", "F80000");
		
		return visualRender.toString();
	}
	
	public String generateSpeedMarkers(Double startLat, Double startLong, int currentSpeed, int speedPlus) {		
				
		if (speedPlus == 0) 
			visualRender.newFolder("Current Speed", false); 
		else
			visualRender.newFolder("Current Speed (+" + speedPlus + ")", false);				
			
		runRouteFromLatLong(startLat, startLong, (currentSpeed+speedPlus)/3.6,8*60,true,false);
		
		return visualRender.toString();	
	}
	
	public String generateStrategyMarkers() {
	
		// Find and load each strategy file
		File folder = new File("C:/TeamArrow/Strategy Drop Folder");
		File[] listOfFiles = null;
		InputStream fis = null;
		BufferedReader br = null;

		if (folder != null)
			listOfFiles = folder.listFiles();
				
		if (listOfFiles != null) {
		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        if (!listOfFiles[i].getName().equals("Available Strategies.csv")) {
		        	
		        	// This should be a strategy file. We will now load it parse it and render it
		        	
		    		try {

		    			fis = new FileInputStream(listOfFiles[i].getCanonicalPath()); 			
		    			InputStreamReader isr = new InputStreamReader(fis);

		    			br = new BufferedReader(isr);

		    			// Remove the first line assuming that it is a header line
		    			br.readLine();
		    						
		    			// No errors up to here... then add a folder
		    			visualRender.newFolder("Strategy - " + listOfFiles[i].getName(), false); 
		    			
		    			// Read the first line of actual data
		    			String line = br.readLine();			

		    			while ((line = br.readLine()) != null) {

		    				// use comma as separator
		    				String[] routeItem = line.split(",");
		    				
		    				visualRender.addPlacemark(Double.parseDouble(routeItem[3]), Double.parseDouble(routeItem[4]), 0, routeItem[0], "ff00ff55");

		    			}

		    			
		    		} catch (FileNotFoundException e) {
		    			e.printStackTrace();
		    		} catch (IOException e) {
		    			e.printStackTrace();
		    		} finally {
		    			if (br != null) {
		    				try {
		    					br.close();
		    				} catch (IOException e) {
		    					e.printStackTrace();
		    				}
		    			}
		    		}		        
		        }
		      } 
		    }			
		}
		
		
		return visualRender.toString();	
	}
	
		
	
	public String generateAllLayers() {
		
		Double startLat = enricherBean.getCurrentLat();
		Double startLong = enricherBean.getCurrentLong();
		int currentSpeed = (int) (enricherBean.getAverageVelocity().doubleValue() * 3.6);
				
		// Clear the render code
		visualRender = new KmlVisualRenderImpl();
		
		// Set us at the start
		try {
			route.setStartNode(0);
		} catch (NoRouteNodeException e) { 
			e.printStackTrace();
		}
		
		// If we have no lat long place us at the start
		if (startLat == -1) startLat = route.getCurrentLatitude();		
		if (startLong == -1) startLong = route.getCurrentLongitude();	
		
		generateRouteMap();			
		
		generateCurrentLatLong(startLat, startLong);
		
		generateSpeedMarkers(startLat, startLong, currentSpeed, 0);
		generateSpeedMarkers(startLat, startLong, currentSpeed, 1);
		generateSpeedMarkers(startLat, startLong, currentSpeed, 2);
		generateSpeedMarkers(startLat, startLong, currentSpeed, 3);
		generateSpeedMarkers(startLat, startLong, currentSpeed, 4);
		generateSpeedMarkers(startLat, startLong, currentSpeed, 5);
		generateSpeedMarkers(startLat, startLong, currentSpeed, 10);
		generateSpeedMarkers(startLat, startLong, currentSpeed, 15);
		generateSpeedMarkers(startLat, startLong, currentSpeed, 20);
		
		generateStrategyMarkers();
		
		generateFlyOver();
		
		return visualRender.toString();
	}
	
	
	public String generateFlyOver() {
		
		int flyOverSectors = 5;	
		int nodesInSector = route.getNumberOfNodes() / flyOverSectors;
		int startNode = 0;
		int endNode = -1;
		
		// Generate a fly-over for the race view
		for ( int i = 1; i <= flyOverSectors; i++ ) {
			visualRender.newTour("Fly Over: Sector " + i, false);
			
			startNode = nodesInSector * (i-1);
			endNode = startNode + nodesInSector;
			
			runRouteFromNodeToNode(startNode,endNode,100/3.6,-1,false,true);
		}
					
		return visualRender.toString();	
	}	
		
	
	public void writeResultToFile(String filename) {
		visualRender.setOutputFile(filename);
		visualRender.write();
	}
	
}
