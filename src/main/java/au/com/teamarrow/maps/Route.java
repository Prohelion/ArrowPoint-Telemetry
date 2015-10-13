package au.com.teamarrow.maps;

import java.util.List;

import au.com.teamarrow.maps.impl.NoRouteNodeException;
import au.com.teamarrow.maps.impl.RouteNode;

public interface Route {

	void setRoute(List<RouteNode> route);
			
	void setRouteFile(String routeFile);
	
	String getRouteFile();
	
	void setStartPoint(double Latitude, double Longitude) throws NoRouteNodeException;
	
	void setStartNode(int nodeNumber) throws NoRouteNodeException;
	
	void gotoStart() throws NoRouteNodeException;
	
	void gotoEnd() throws NoRouteNodeException;
	
	void gotoNextNode() throws NoRouteNodeException;
			
	void gotoNearestNode(double latitude, double longitude) throws NoRouteNodeException;
	
	void goForwards(double distance) throws NoRouteNodeException;
	
	boolean isRouteComplete();
	
	double getSectorDistance();
	
	double getSectorDistanceRemaining();
		
	double getTotalDistanceTravelled();
	
	double getTotalDistanceRemaining();
	
	double getTotalRouteDistance();
			
	double getCurrentLatitude();
	
	double getCurrentLongitude();
	
	double getCurrentElevation();
	
	int getCurrentSector();
	
	int getNumberOfNodes();
	
	boolean atScheduledStop();
	
	boolean atEnd();
	
	String getScheduledStopDescription();
	
	RouteNode getRouteNode(int nodeIndex);
	
	public double getDistanceToNextScheduledStop();
	
	public void setControlStopDistances(int[] controlStopDistances);
	
	public void setControlStopDescriptions(String[] controlStopDescriptions);
	
	public int[] getControlStopDistances();

	public String[] getControlStopDescriptions();
	
}
