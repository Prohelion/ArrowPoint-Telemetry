package com.prohelion.maps.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.prohelion.maps.Route;
import com.prohelion.maps.SimulationData;
import com.prohelion.maps.VisualRender;
import com.prohelion.service.impl.MeasurementDataEnrichment;

import de.micromata.opengis.kml.v_2_2_0.Folder;

@Component
public class MapGenerator {

	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(MapGenerator.class);
	
	@Autowired
    private MeasurementDataEnrichment enricherBean;
	
	@Autowired
	private Environment env;
	
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
			visualRender.newFolder("Current Speed", false, null); 
		else
			visualRender.newFolder("Current Speed (+" + speedPlus + ")", false, null);				
			
		runRouteFromLatLong(startLat, startLong, (currentSpeed+speedPlus)/3.6,8*60,true,false);
		
		return visualRender.toString();	
	}
	
	public String generateStrategyMarkers() {
	
		// Find and load each strategy file
		File folder = new File(env.getProperty("strategy.drop.folder"));
		File[] listOfFiles = null;
		InputStream fis = null;
		BufferedReader br = null;
		Integer sectorCount = 1;		
		Folder strategyFolder = null;

		if (folder != null)
			listOfFiles = folder.listFiles();
				
		if (listOfFiles != null) {
		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        if (!listOfFiles[i].getName().equals("Available Strategies.csv")) {
		        	
		        	// This should be a strategy file. We will now load it parse it and render it
		        	
		    		try {

		    			sectorCount = 1;
		    			
		    			fis = new FileInputStream(listOfFiles[i].getCanonicalPath()); 			
		    			InputStreamReader isr = new InputStreamReader(fis);

		    			br = new BufferedReader(isr);

		    			// Remove the first line assuming that it is a header line
		    			br.readLine();
		    						
		    			// No errors up to here... then add a folder
		    			visualRender.newFolder("Strategy - " + listOfFiles[i].getName(), false, null);
		    			strategyFolder = visualRender.getCurrentFolder();
		    			
		    			visualRender.newFolder("Sector - " + sectorCount, false,strategyFolder);
		    			sectorCount++;
		    			
		    			// Read the first line of actual data
		    			String line = br.readLine();		
		    			
		    			boolean stop = false;
		    			String message = "";

		    			while ((line = br.readLine()) != null) {

		    				// use comma as separator
		    				String[] routeItem = line.split(",");
		    				
		    				String status = null;
		    				String colour = "501400FF";
		    				String formattedDate = routeItem[0];
		    				String speedDistance = "(" + new Double(routeItem[1]).intValue() + "kph, " + new Double(routeItem[2]).intValue() + "km)";
		    						    			
							try {
								Date date = new SimpleDateFormat("dd-MMM-yyy HH:mm:ss").parse(routeItem[0]);
								formattedDate = new SimpleDateFormat("dd-MMM HH:mm").format(date);
							} catch (ParseException e) {
								LOG.error("Strategy date error loading file, date provided " + routeItem[0]);							
							}		    						    					    			
		    				
		    				if (routeItem.length >= 13) status = routeItem[12]; 
		    						    				
		    				if (status == null) {
		    					
		    					if ( stop == true ) {
		    						message = "Restart - " + formattedDate + speedDistance;
		    						stop = false;
		    					} else {
		    						message = formattedDate + speedDistance;
		    					}		    						
		    					
		    					colour = "5000E614";
	    						visualRender.addPlacemark(Double.parseDouble(routeItem[4]), Double.parseDouble(routeItem[5]), Double.parseDouble(routeItem[6]), message, colour);
		    				}
		    				else if (status.toUpperCase().equals("NIGHT STOP")) {
		    					colour = "50000000";
		    					message = "Night Stop - " + formattedDate + speedDistance;
		    					
		    					// If we have just stopped, log one marker.
		    					if (stop == false)
		    						visualRender.addPlacemark(Double.parseDouble(routeItem[4]), Double.parseDouble(routeItem[5]), Double.parseDouble(routeItem[6]), message, colour);
		    					stop = true;
		    				}
		    				else if (status.toUpperCase().equals("CONTROL STOP")) {
		    					colour = "501400FF";
		    					message = "Control Stop - " + formattedDate + speedDistance;
		    					
		    					// If we have just stopped, log one marker then create a new folder
		    					if (stop == false) {
		    						visualRender.addPlacemark(Double.parseDouble(routeItem[4]), Double.parseDouble(routeItem[5]), Double.parseDouble(routeItem[6]), message, colour);
		    						// No errors up to here... then add a folder
		    						visualRender.newFolder("Sector - " + sectorCount, false,strategyFolder);
		    						sectorCount++;
		    					}
		    					
		    					stop = true;
		    				}

		    						    					
		    					
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

		generateSpeedMarkers(startLat, startLong, currentSpeed, -10);
		generateSpeedMarkers(startLat, startLong, currentSpeed, -5);
		generateSpeedMarkers(startLat, startLong, currentSpeed, -4);
		generateSpeedMarkers(startLat, startLong, currentSpeed, -3);
		generateSpeedMarkers(startLat, startLong, currentSpeed, -2);
		generateSpeedMarkers(startLat, startLong, currentSpeed, -1);
		generateSpeedMarkers(startLat, startLong, currentSpeed, 0);
		generateSpeedMarkers(startLat, startLong, currentSpeed, 1);
		generateSpeedMarkers(startLat, startLong, currentSpeed, 2);
		generateSpeedMarkers(startLat, startLong, currentSpeed, 3);
		generateSpeedMarkers(startLat, startLong, currentSpeed, 4);
		generateSpeedMarkers(startLat, startLong, currentSpeed, 5);
		generateSpeedMarkers(startLat, startLong, currentSpeed, 10);
		
		generateStrategyMarkers();
		
		generateFlyOver();
		
		return visualRender.toString();
	}
	
	
	public String generateFlyOver() {
		
		int flyOverSectors = 8;			
		int sectorStartNode = 0;
		int sectorEndNode = -1;
		int extendedEndNode = -1;
		double crossoverPercentage = .1;
		double distanceInSector = -1;
		double extendedDistanceInSector = -1;
		int sectorCount = 1;

		try {
			
			route.setStartNode(0);
			
			distanceInSector = (route.getTotalRouteDistance()) / flyOverSectors;
			
			// Now extend this by the crossOverPercentage
			extendedDistanceInSector = distanceInSector + (distanceInSector * crossoverPercentage);
			
			route.goForwards(distanceInSector);
			sectorEndNode = route.getCurrentSector();
		
			route.setStartNode(sectorStartNode);		
			route.goForwards(extendedDistanceInSector);
			extendedEndNode = route.getCurrentSector();
				
			// SectorendNode is to deal with a condition where the route does not load.
			// In which case this can result in an infinite loop
			while (route.atEnd() == false && sectorEndNode > 0) {
				visualRender.newTour("Fly Over: Sector " + sectorCount, false);
				runRouteFromNodeToNode(sectorStartNode,extendedEndNode,100/3.6,-1,false,true);
	
				sectorStartNode = sectorEndNode;
				
				if (sectorEndNode > 0) {
				
					route.setStartNode(sectorStartNode);			
					route.goForwards(distanceInSector);
					sectorEndNode = route.getCurrentSector();
					
					route.setStartNode(sectorStartNode);			
					route.goForwards(extendedDistanceInSector);
					extendedEndNode = route.getCurrentSector();
							
					sectorCount++;
				}
			}

		} catch (NoRouteNodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return visualRender.toString();	
	}	
		
	
	public void writeResultToFile(String filename) {
		visualRender.setOutputFile(filename);
		visualRender.write();
	}
	
}
