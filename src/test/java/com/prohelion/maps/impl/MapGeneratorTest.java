package com.prohelion.maps.impl;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.prohelion.maps.Route;
import com.prohelion.maps.impl.MapGenerator;
import com.prohelion.service.impl.MeasurementDataEnrichment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class MapGeneratorTest {

	private ApplicationContext context;
	
	Route route;
	
	@Autowired
	MapGenerator mapGenerator;
	
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
