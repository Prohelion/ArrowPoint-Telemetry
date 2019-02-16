package com.prohelion.maps.impl;

import com.prohelion.maps.Environment;
import com.prohelion.maps.Route;
import com.prohelion.maps.SimulationData;

public class SimulationDataImpl implements SimulationData {

	int iterationIndex;
	double targetSpeedInMPS;
	double actualSpeedInMPS;
	double batteryStateOfCharge;
	int simulationInterval;
	int batteryStatus = SimulationData.BATTERY_OK;
	Environment environment;
	Route route;
	
	public void setIterationIndex(int index) {
		this.iterationIndex = index;
	}

	public int getIterationIndex() {
		return this.iterationIndex;
	}
	
	public void incrementIterationIndex() {
		this.iterationIndex++;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public Environment getEnvironment() {
		return this.environment;
	}

	public int getSimulationInterval() {
		return simulationInterval;
	}

	public void setSimulationInterval(int interval) {
		this.simulationInterval = interval;
	}

	public double getActualSpeed() {
		return this.actualSpeedInMPS;
	}

	public void setActualSpeed(double speedInMPS) {
		this.actualSpeedInMPS = speedInMPS;		
	}	
	
	public double getTargetSpeed() {
		return this.targetSpeedInMPS;
	}

	public void setTargetSpeed(double speedInMPS) {
		this.targetSpeedInMPS = speedInMPS;		
	}

	public int getBatteryStatus() {		
		return this.batteryStatus;
	}

	public void setBatteryStatus(int batteryStatus) {
		this.batteryStatus = batteryStatus;		
	}

	public void setRoute(Route route) {
		this.route = route;		
	}

	public Route getRoute() {
		return this.route;
	}

	public double getBatteryStateOfCharge() {
		return this.batteryStateOfCharge;		
	}

	public void setBatteryStateOfCharge(double batteryStateOfCharge) {
		this.batteryStateOfCharge = batteryStateOfCharge;		
	}

}
