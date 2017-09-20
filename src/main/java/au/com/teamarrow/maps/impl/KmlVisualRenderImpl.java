package au.com.teamarrow.maps.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import au.com.teamarrow.maps.VisualRender;
import de.micromata.opengis.kml.v_2_2_0.AltitudeMode;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.IconStyle;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.LabelStyle;
import de.micromata.opengis.kml.v_2_2_0.LineString;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Style;
import de.micromata.opengis.kml.v_2_2_0.gx.FlyToMode;
import de.micromata.opengis.kml.v_2_2_0.gx.Playlist;
import de.micromata.opengis.kml.v_2_2_0.gx.Tour;

public class KmlVisualRenderImpl implements VisualRender {

	private Kml kml;
	private Document kmlDocument;		
	private Tour kmlTour;
	private Playlist playlist;
	private Folder folder;
	private LineString kmlRoute;	
	private String filename = null;
	private final String pushPinImg = "http://maps.google.com/mapfiles/kml/pushpin/wht-pushpin.png";
	
	KmlVisualRenderImpl () {
		kml = new Kml();
		kmlDocument = kml.createAndSetDocument();							
		Placemark placemark = kmlDocument.createAndAddPlacemark();
		placemark.setVisibility(true);
		
		kmlRoute = placemark.createAndSetLineString();	
	}
	
	public Folder getCurrentFolder() {
		return folder;
	}
	
	public void newFolder(String name, boolean visible, Folder currentFolder) {
		
		if (currentFolder == null)
			folder = kmlDocument.createAndAddFolder();
		else
			folder = currentFolder.createAndAddFolder();
				
		folder.setName(name);
		folder.setVisibility(visible);				
		
		Placemark placemark = folder.createAndAddPlacemark();
		placemark.setVisibility(visible);
		
		kmlRoute = placemark.createAndSetLineString();	
	}
	
	public void newTour(String tourName, boolean visible) {
		kmlTour = kmlDocument.createAndAddTour().withName(tourName);
		kmlTour.setVisibility(visible);
		playlist = kmlTour.createAndSetPlaylist();
	}
	
	
	/**
	 * This method adds "spots" to the map
	 * which it then connects up with a line
	 * 
	 * @param double latitude
	 * @param double longitude
	 * @param double elevation
	 */
	public void addToPath(double latitude, double longitude, double elevation) {
			kmlRoute.addToCoordinates(longitude, latitude, elevation);		
	}

	public void addToTour(double latitude, double longitude, double elevation) {
		playlist.createAndAddFlyTo()
		.withDuration(3)
		.withFlyToMode(FlyToMode.SMOOTH)
		.createAndSetLookAt()
			.withAltitudeMode(AltitudeMode.CLAMP_TO_GROUND)
			.withRange(200)
			.withTilt(45)
			.withHeading(0)
			.withLatitude(latitude)
			.withLongitude(longitude);
		
	}
	
	
	/**
	 * Adds a placemark using KML
	 * 
	 *  NOTE: If you want to colour a placemark
	 *  pin, you need to use the white pin and 
	 *  specify the colour - the colour is 
	 *  overlaid ontop of the white pin.
	 *  
	 *  In order to create a new pin icon, you first
	 *  need to create a new IconStyle and then 
	 *  a new Icon. The Icon is where you specify 
	 *  the URL of the icon and IconStyle is where
	 *  you specify the colour you want to overlay
	 *  e.g. (slightly dumbed down)
	 *  <Style>
	 *  	<IconStyle>
	 *  		<color>ff00ff55</color>
	 *  		<Icon>
	 *  			<href>http://maps.google.com/mapfiles/kml/pushpin/wht-pushpin.png</href>
	 *  		</Icon>
	 *  	</IconStyle>
	 *  	<LabelStyle>
	 *  		<color>ff00ffff</color>
	 *  	</LabelStyle>
	 *  </Style>  
	 *  
	 * @param double latitude
	 * @param double longitude
	 * @param double elevation
	 * @param String description 
	 * @param String colour
	 */
	public void addPlacemark(double latitude, double longitude, double elevation,
			String description, String colour) {
			
		Placemark placemark = null;		
		
		// Create a new placemark
		if (folder == null) {		
			placemark = kmlDocument.createAndAddPlacemark();
			placemark.setVisibility(kmlDocument.isVisibility());
		} else {
			placemark = folder.createAndAddPlacemark();
			placemark.setVisibility(folder.isVisibility());
		}			
			
		placemark.withName(description).withOpen(Boolean.FALSE);
		placemark.createAndSetPoint().addToCoordinates(longitude, latitude, elevation);	  
		
		// Create our Style 
		Style style = placemark.createAndAddStyle();				
		
		// Create the label 
		LabelStyle labelStyle = new LabelStyle();;
		labelStyle.setColor(colour);
		labelStyle.setScale(1);	
		
		// Create the icon style <IconStyle></IconStyle>
		IconStyle iconStyle = new IconStyle();
		iconStyle.setColor(colour);
		iconStyle.setScale(1);
		
		// Create the actual icon <IconStyle><icon></icon></IconStyle>
		Icon icon = new Icon();
		icon.setHref(pushPinImg);

		// Add the icon into the IconStyle
		iconStyle.setIcon(icon);
		
		// Add LabelStyle and IconStyle to Style
		style.setLabelStyle(labelStyle);
		style.setIconStyle(iconStyle);
		

	}
	
	
	/**
	 * Returns the KML structure as a string
	 * 
	 * 
	 *  
	 */
	public String toString() {
		
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		
		try {
			kml.marshal(baOutputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return(null);
		}
		
		try {
			return baOutputStream.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return(null);
		}
		
	}
	

	/**
	 * Sets the output file that the KML file
	 * will be written to 
	 * 
	 * @param String filename
	 */
	public void setOutputFile(String filename) {
		this.filename = filename;		
	}

	
	/**
	 * Writes the KML to the file
	 */
	public void write() {
		
		try {
			kml.marshal(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
