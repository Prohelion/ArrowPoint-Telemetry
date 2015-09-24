package au.com.teamarrow.maps.impl;

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
		mapGenerator.writeResultToFile("D:/test.kml");		
	}	
		
	
	@Before
    public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("ArrowPointMaps-test.xml");
		
		route = (Route) context.getBean("Route");				
	}
	
}
