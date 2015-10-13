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
		mapGenerator.setMeasurementDataEnrichment(new MeasurementDataEnrichment());
		mapGenerator.setRoute(route);		
		mapGenerator.generateAllLayers();			
		
		String filename = System.getProperty("java.io.tmpdir") + "/test.kml";
		
		mapGenerator.writeResultToFile(filename);
		
		File file = new File(filename);
        file.delete();
	}	
		
	
	@Before
    public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("spring-test.xml");
		
		route = (Route) context.getBean("Route");				
	}
	
}
