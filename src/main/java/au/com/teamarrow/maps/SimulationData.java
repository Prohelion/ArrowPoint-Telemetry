package au.com.teamarrow.maps;

public interface SimulationData {

	public static int BATTERY_FLAT = 0;
	public static int BATTERY_OK = 1;
	
	public void setIterationIndex(int index);
	
	public int getIterationIndex();
	
	public void incrementIterationIndex();
	
	public int getSimulationInterval();
	
	public void setSimulationInterval(int interval);
	
	public double getTargetSpeed();
	
	public void setTargetSpeed(double speedInMPS);

	public double getActualSpeed();
	
	public void setActualSpeed(double speedInMPS);
	
	public void setEnvironment(Environment environment);
	
	public Environment getEnvironment();

	public void setRoute(Route route);
	
	public Route getRoute();
		
	public int getBatteryStatus();
	
	public void setBatteryStatus(int batteryStatus);
	
	public double getBatteryStateOfCharge();
	
	public void setBatteryStateOfCharge(double batteryStateOfCharge);

	
}


