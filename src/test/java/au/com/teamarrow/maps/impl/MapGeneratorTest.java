package au.com.teamarrow.maps.impl;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import au.com.teamarrow.maps.Route;
import au.com.teamarrow.service.impl.MeasurementDataEnrichment;

public class MapGeneratorTest {

	private ApplicationContext context;
	
	Route route;
	MapGenerator mapGenerator = new MapGenerator();
	
	@Test
	public void getKML() {
		
		boolean generateToD_Drive=false;
		
		mapGenerator.setMeasurementDataEnrichment(new MeasurementDataEnrichment());
		mapGenerator.setRoute(route);		
		mapGenerator.generateAllLayers();			
		
		String filename = System.getProperty("java.io.tmpdir") + "/test.kml";
		
		if (generateToD_Drive) filename = "D:/test.kml";
		
		mapGenerator.writeResultToFile(filename);
		
		if (!generateToD_Drive){
			File file = new File(filename);
        	file.delete();
		}
	}	
		
	
	@Before
    public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("spring-test.xml");
		
		route = (Route) context.getBean("Route");				
	}
	
}
