package au.com.teamarrow.maps.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import au.com.teamarrow.maps.Route;

public class CvsRouteImpl implements Route {
	
	private static final Logger LOG = LoggerFactory.getLogger(CvsRouteImpl.class);

	GeoSpatialUtilities geoUtils = new GeoSpatialUtilities();	
	List<RouteNode> route = new ArrayList<RouteNode>();
	//double totalRouteDistance;
	double sectorDistanceRemaining;
	int currentNode = 0;
	int[] controlStopDistances;
	int numberOfControlStops;
	String routeFile = new String();
	boolean enableRoute = false;

	String[] controlStopDescriptions;
	int scheduleStopNo = 0;

	@Override
	public void setEnableRoute(boolean enableRoute) {
		this.enableRoute = enableRoute;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see au.com.teamarrow.simulator.Route#setRoute(java.util.List)
	 */
	public void setRoute(List<RouteNode> route) {
		this.route = route;
	}

	public void setControlStopDistances(int[] controlStopDistances) {
		
		this.controlStopDistances = controlStopDistances;
		this. numberOfControlStops = controlStopDistances.length;
		
	}
	
	public void setControlStopDescriptions(String[] controlStopDescriptions) {
		this.controlStopDescriptions = controlStopDescriptions;	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.com.teamarrow.simulator.Route#setRouteFile(java.lang.String)
	 */
	public void setRouteFile(String routeFile) {

		if (enableRoute == false) return;
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		
		this.routeFile = routeFile;
		
		RouteNode previousPoint = new RouteNode();
		RouteNode currentPoint = new RouteNode();
		route = new ArrayList<RouteNode>();

		GeoSpatialUtilities geoUtils = new GeoSpatialUtilities();

		try {

			InputStream fis = null;
			
			// Try to load it from inside the package (war) first.... If that doesn't work look on the file system
			fis = this.getClass().getResourceAsStream(routeFile);
			if (fis == null) fis = new FileInputStream(routeFile); 			
			
			InputStreamReader isr = new InputStreamReader(fis);

			br = new BufferedReader(isr);

			// Remove the first line assuming that it is a header line
			br.readLine();
						
			// Read the first line of actual data
			line = br.readLine();
			String[] routeItem = line.split(cvsSplitBy);

			previousPoint.setLatitude(Double.parseDouble(routeItem[0]));
			previousPoint.setLongitude(Double.parseDouble(routeItem[1]));
			previousPoint.setElevation(Double.parseDouble(routeItem[2]));
			
			//totalRouteDistance = 0;

			while ((line = br.readLine()) != null) {

				// use comma as separator
				routeItem = line.split(cvsSplitBy);

				currentPoint = new RouteNode();
				currentPoint.setLatitude(Double.parseDouble(routeItem[0]));
				currentPoint.setLongitude(Double.parseDouble(routeItem[1]));
				currentPoint.setElevation(Double.parseDouble(routeItem[2]));

				previousPoint.setGradient(geoUtils.gradient(
						previousPoint.getLatitude(),
						currentPoint.getLatitude(),
						previousPoint.getLongitude(),
						currentPoint.getLongitude(),
						previousPoint.getElevation(),
						currentPoint.getElevation()));

				previousPoint.setDistance(geoUtils.distance(
						previousPoint.getLatitude(),
						currentPoint.getLatitude(),
						previousPoint.getLongitude(),
						currentPoint.getLongitude(),
						previousPoint.getElevation(),
						currentPoint.getElevation()));

				// @todo:  This doesn't seem to work so I am post calcing it currently
				//totalRouteDistance = totalRouteDistance + previousPoint.getDistance();

				route.add(previousPoint);

				previousPoint = currentPoint;
			}

			// Add the last node
			route.add(currentPoint);
			
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
		
		currentNode = 0;
		sectorDistanceRemaining = route.get(currentNode).getDistance();

	}
	
	
	public String getRouteFile() {
		return this.routeFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.com.teamarrow.simulator.Route#setStartPoint(double, double)
	 */
	public void setStartPoint(double latitude, double longitude)
			throws NoRouteNodeException {

		// The reason that we add one is that a logical error can occur, where
		// the closest
		// node is actually behind you. By adding one the node is always further
		// down
		// the path, even if is actually not the closest point.
		currentNode = getNearestNode(latitude, longitude) + 1;

		RouteNode routeNode = route.get(currentNode);

		sectorDistanceRemaining = geoUtils.distance(latitude,
				routeNode.getLatitude(), longitude, routeNode.getLongitude(),
				routeNode.getElevation(), routeNode.getElevation());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.com.teamarrow.simulator.Route#setStartNode(int)
	 */
	public void setStartNode(int nodeNumber) throws NoRouteNodeException {
		if (route == null)
			throw new NoRouteNodeException("No route has been loaded");
		if (nodeNumber > getNumberOfNodes())
			throw new NoRouteNodeException(
					"The start node that you have passed is beyond the end of the route");

		currentNode = nodeNumber;
		sectorDistanceRemaining = route.get(currentNode).getDistance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.com.teamarrow.simulator.Route#getNextNode()
	 */
	public RouteNode getFirstNode() throws NoRouteNodeException {
		if (route == null)
			throw new NoRouteNodeException("No route has been loaded");
		return (route.get(0));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.com.teamarrow.simulator.Route#getNextNode()
	 */
	public RouteNode getNextNode() throws NoRouteNodeException {
		if (route == null)
			throw new NoRouteNodeException("No route has been loaded");

		currentNode++;
		if (currentNode > route.size() + 1)
			throw new NoRouteNodeException(
					"We have reached the end of the route, there are no more nodes available");

		sectorDistanceRemaining = route.get(currentNode).getDistance();

		return (route.get(currentNode));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.com.teamarrow.simulator.Route#getNearestNode()
	 */
	private int getNearestNode(double latitude, double longitude)
			throws NoRouteNodeException {

		double distance;
		double closestDistance = Double.MAX_VALUE;
		int closestNode = -1;
		int nodeIndex = 0;

		Iterator<RouteNode> iterator = route.iterator();
		while (iterator.hasNext()) {
			RouteNode node = iterator.next();

			distance = geoUtils.distance(latitude, node.getLatitude(),
					longitude, node.getLongitude(), node.getElevation(),
					node.getElevation());

			// If this is closer, then set this to be the closest distance
			if (distance < closestDistance) {
				closestDistance = distance;
				closestNode = nodeIndex;
				LOG.debug("A new closest node has been found, with a distance of : "
						+ distance + "m");
			}

			nodeIndex++;
		}

		if (closestNode == -1)
			throw new NoRouteNodeException(
					"No nearest route node has been able to be found");

		return (closestNode);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.com.teamarrow.simulator.Route#isRouteComplete()
	 */
	public boolean isRouteComplete() {
		return (currentNode >= route.size() - 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.com.teamarrow.simulator.Route#getTotalRouteDistance()
	 */
	/* public double getTotalRouteDistance() {
		return totalRouteDistance;
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.com.teamarrow.simulator.Route#getNumberOfNodes()
	 */
	public int getNumberOfNodes() {
		return route.size();
	}

	public void gotoStart() throws NoRouteNodeException {
		if (route == null || route.size() == 0)
			throw new NoRouteNodeException("No route appears to have been loaded");

		currentNode = 0;
		sectorDistanceRemaining = route.get(currentNode).getDistance();

	}

	public void gotoEnd() throws NoRouteNodeException {

		if (route == null || route.size() == 0)
			throw new NoRouteNodeException("No route appears to have been loaded");

		//TODO: This needs testing
		currentNode = route.size() - 1;
		sectorDistanceRemaining = 0;

	}

	
	public void gotoNextNode() throws NoRouteNodeException {
		currentNode++;

		if (route == null || currentNode > route.size())
			throw new NoRouteNodeException(
					"We have moved beyond the end of the route or no route has been loaded");
	}

	public void goForwards(double distance) throws NoRouteNodeException {

		// First check to make sure we actually have a route set
		if ((route == null) || (route.size() == 0)) {
			throw new NoRouteNodeException("No route has been loaded, so we have nowhere to go");
		}
		
		// If we don't transition a node boundary then this is all fine
		if (distance < sectorDistanceRemaining) {
			sectorDistanceRemaining = sectorDistanceRemaining - distance;
			return;
		}

		// If we do transition a node boundary, we need to figure out how many
		distance = distance - sectorDistanceRemaining;
		currentNode++;	
		
		if (isRouteComplete()) {
			sectorDistanceRemaining = 0;
			return;
		}	
		
		while (distance >= route.get(currentNode).getDistance()) {
			distance = distance - route.get(currentNode).getDistance();
			currentNode++;
			
			if (isRouteComplete()) {
				sectorDistanceRemaining = 0;
				return;
			}	
		}

		sectorDistanceRemaining = route.get(currentNode).getDistance()
				- distance;

	}

	public double getSectorDistance() {
		return (route.get(currentNode).getDistance());
	}

	public double getSectorDistanceRemaining() {
		return sectorDistanceRemaining;
	}

	public double getTotalDistanceTravelled() {
		
		double totalDistance = 0;

		for (int countIndex = 0; countIndex < currentNode; countIndex++) {
			totalDistance = totalDistance
					+ route.get(countIndex).getDistance();
		}

		totalDistance = totalDistance
				+ (route.get(currentNode).getDistance() - sectorDistanceRemaining);

		return totalDistance;	
	}
	
	public double getTotalDistanceRemaining() {

		double totalDistanceRemaining = 0;

		for (int countIndex = currentNode + 1; countIndex < route.size(); countIndex++) {
			totalDistanceRemaining = totalDistanceRemaining
					+ route.get(countIndex).getDistance();
		}

		totalDistanceRemaining = totalDistanceRemaining
				+ sectorDistanceRemaining;

		return totalDistanceRemaining;
	}
	

	
	public double getTotalRouteDistance() {

		double totalRouteDistance = 0;

		for (int countIndex = 0; countIndex < route.size(); countIndex++) {
			totalRouteDistance = totalRouteDistance
					+ route.get(countIndex).getDistance();
		}

		return totalRouteDistance;
	}

	
	public void gotoNearestNode(double latitude, double longitude)
			throws NoRouteNodeException {
		currentNode = getNearestNode(latitude, longitude);
	}

	/**
	 * FIXME (?) I think this is broken. Doesn't give the correct latitude 
	 * if you're at any other point then the end of the route
	 * I believe it has something to do with the getSectorDistance() method
	 */
	public double getCurrentLatitude() {
		double percentageTravelled = (this.getSectorDistance() - sectorDistanceRemaining) / this.getSectorDistance();
		
		if (this.isRouteComplete()) return(route.get(currentNode).getLatitude());
					
		double currentEstLatitude = route.get(currentNode).getLatitude() + 
						  (route.get(currentNode + 1).getLatitude() - route.get(currentNode).getLatitude()) * percentageTravelled; 
		
		return currentEstLatitude;
	}

	public double getCurrentLongitude() {
		double percentageTravelled = (this.getSectorDistance() - sectorDistanceRemaining) / this.getSectorDistance();
		
		if (this.isRouteComplete()) return(route.get(currentNode).getLongitude());
					
		double currentEstLongitude = route.get(currentNode).getLongitude() + 
						  (route.get(currentNode + 1).getLongitude() - route.get(currentNode).getLongitude()) * percentageTravelled; 
		
		return currentEstLongitude;
	}

	
	public double getCurrentElevation() {
		double percentageTravelled = (this.getSectorDistance() - sectorDistanceRemaining) / this.getSectorDistance();
		
		if (this.isRouteComplete()) return(route.get(currentNode).getElevation());
					
		double currentEstElevation = route.get(currentNode).getElevation() + 
						  (route.get(currentNode + 1).getElevation() - route.get(currentNode).getElevation()) * percentageTravelled; 
		
		return currentEstElevation;
	}

	public int getCurrentSector() {		
		return currentNode;
	}
	
	public RouteNode getRouteNode(int nodeIndex){
		return route.get(nodeIndex);
	}

	public boolean atScheduledStop(){
		
		boolean areWeStopped = false;
		
		//if we still have places to stop at
		if (scheduleStopNo < numberOfControlStops){
		
			if (getTotalDistanceTravelled() > controlStopDistances[scheduleStopNo]){
				
				scheduleStopNo++;
				areWeStopped = true;		
			} 
		} 
		
		return areWeStopped;
	}
	
	public boolean atEnd() {
						
		if (route == null) return(true);
		if (currentNode >= route.size()) return(true);		
		return(false);
		
	}

	public String getScheduledStopDescription() {
		
		return controlStopDescriptions[scheduleStopNo - 1];
	}
	
	public double getDistanceToNextScheduledStop(){
		
		if (scheduleStopNo < numberOfControlStops){
			return controlStopDistances[scheduleStopNo] - getTotalDistanceTravelled();
		} else {
			return -1;
		}
	}

	public int[] getControlStopDistances() {
		return controlStopDistances;
	}

	public String[] getControlStopDescriptions() {
		return controlStopDescriptions;
	}

	
}