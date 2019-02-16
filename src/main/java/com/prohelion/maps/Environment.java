package com.prohelion.maps;

import java.util.GregorianCalendar;


public interface Environment {

	double getLatitude();
	
	void setLatitude(double latitude);
	
	double getLongitude();
	
	void setLongitude(double longitude);
	
	double getGradient();
	
	void setGradient(double gradient);
		
	GregorianCalendar getGregorianCalendar();
	
	void setGregorianCalendar(GregorianCalendar calendar);
	
	int getWindDirection();
	
	void setWindDirection(int direction);
	
	int getWindSpeed();
	
	void setWindSpeed(int speed);
	
}
