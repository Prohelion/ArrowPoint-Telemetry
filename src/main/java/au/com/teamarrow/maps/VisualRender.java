package au.com.teamarrow.maps;

public interface VisualRender {

	public void addToPath(double latitude, double longitude, double elevation);
	
	public void addToTour(double latitude, double longitude, double elevation);
	
	public void addPlacemark(double latitude, double longitude, double elevation, String description, String colour);
	
	public void newFolder(String name, boolean visible);
	
	public void newTour(String tourName, boolean visible);
	
	public void setOutputFile(String filename);
	
	public void write();
	
	public String toString();
	
}
