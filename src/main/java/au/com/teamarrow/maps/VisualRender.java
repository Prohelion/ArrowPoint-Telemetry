package au.com.teamarrow.maps;

import de.micromata.opengis.kml.v_2_2_0.Folder;

public interface VisualRender {

	public void addToPath(double latitude, double longitude, double elevation);
	
	public void addToTour(double latitude, double longitude, double elevation);
	
	public void addPlacemark(double latitude, double longitude, double elevation, String description, String colour);
	
	public void newFolder(String name, boolean visible, Folder folder);
	
	public Folder getCurrentFolder();
	
	public void newTour(String tourName, boolean visible);
	
	public void setOutputFile(String filename);
	
	public void write();
	
	public String toString();
	
}
