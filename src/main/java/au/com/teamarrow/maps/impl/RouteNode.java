package au.com.teamarrow.maps.impl;

public class RouteNode {

	private double latitude;
	private double longitude;
	private double gradient;
	private double elevation;
	private double distance;

	public RouteNode() { }
	
	public RouteNode(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.elevation = 0;
	}
	
	public RouteNode(double latitude, double longitude, double elevation) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.elevation = elevation;		
	}
	
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getElevation() {
		return elevation;
	}
	public void setElevation(double elevation) {
		this.elevation = elevation;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getGradient() {
		return gradient;
	}
	public void setGradient(double gradient) {
		this.gradient = gradient;
	}
	
	
}
