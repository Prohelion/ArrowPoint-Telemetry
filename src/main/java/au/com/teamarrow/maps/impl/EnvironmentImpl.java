package au.com.teamarrow.maps.impl;

import java.util.GregorianCalendar;

import au.com.teamarrow.maps.Environment;

public class EnvironmentImpl implements Environment {

	private GregorianCalendar calendar;
	private double latitude;
	private double longitude;
	
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
		// TODO Auto-generated method stub
		return 0;
	}

	
	public void setGradient(double gradient) {
		// TODO Auto-generated method stub
		
	}

	
	public int getWindDirection() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public void setWindDirection(int direction) {
		// TODO Auto-generated method stub
		
	}

	
	public int getWindSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public void setWindSpeed(int speed) {
		// TODO Auto-generated method stub
	}


	
	public GregorianCalendar getGregorianCalendar() {		
		return this.calendar;
	}

	
	public void setGregorianCalendar(GregorianCalendar calendar) {
		this.calendar = calendar;
	}

}
