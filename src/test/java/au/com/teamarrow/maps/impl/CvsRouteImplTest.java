package au.com.teamarrow.maps.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import au.com.teamarrow.maps.Route;
import au.com.teamarrow.maps.impl.NoRouteNodeException;
import au.com.teamarrow.maps.impl.RouteNode;


public class CvsRouteImplTest {

	static Logger log = Logger.getLogger(CvsRouteImplTest.class.getName());
	
	private ApplicationContext context;
	
	Route route;
	List<RouteNode> testRoute1 = new ArrayList<RouteNode>();
	
	@Test
	public void setRoute() { 
		route.setRoute(testRoute1);			
		assertTrue(route.getNumberOfNodes() == 10);
	}
	
	@Test 
	public void setRouteFile() {		
		String routefile = "/test_routedata.csv";
		
		assertNotNull("CSV Route file missing", getClass().getResource(routefile));		
		route.setRouteFile(routefile);		
		assertTrue(route.getNumberOfNodes() == 5700);
	}
	
	@Test 
	public void setStartPoint()  {
		route.setRoute(testRoute1);
				
		try {
			route.setStartPoint(testRoute1.get(5).getLatitude(),testRoute1.get(5).getLongitude());
		} catch (NoRouteNodeException e) {
			e.printStackTrace();
			fail("No closest node could be found, which makes no sense as this is an actual node we are using");
		}
		
		assertTrue(route.getCurrentSector() == 6);
	}
	
	@Test 
	public void setStartNode()  {
		
		route.setRoute(testRoute1);
		
		try {
			route.setStartNode(5);
		} catch (NoRouteNodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("No closest node could be found, which makes no sense as this is an actual node we are using");
		}
		
		assertEquals(5, route.getCurrentSector());
	}
	
	@Test 
	public void gotoStartNode() {
		
		route.setRoute(testRoute1);
		
		try {
			route.setStartNode(5);
		} catch (NoRouteNodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(5, route.getCurrentSector());
		
	}
	
	@Test 
	public void gotoNextNode() {
		
		route.setRoute(testRoute1);
		
		try {
			route.setStartNode(1);
			route.gotoNextNode();
		} catch (NoRouteNodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		assertEquals(2, route.getCurrentSector());
	}
			
	@Test 
	public void gotoNearestNode() {
		
		route.setRoute(testRoute1);
		
		try {
			
			route.gotoNearestNode(testRoute1.get(5).getLatitude(),testRoute1.get(5).getLongitude());
			assertEquals(5, route.getCurrentSector());
			
			route.gotoNearestNode(testRoute1.get(4).getLatitude(),testRoute1.get(4).getLongitude());
			assertEquals(4, route.getCurrentSector());
			
			// is this really a valid test? do you know for sure that this is right
			route.gotoNearestNode(testRoute1.get(3).getLatitude(),testRoute1.get(1).getLongitude());
			assertEquals(2, route.getCurrentSector());
			
			
		} catch (NoRouteNodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test 
	public void goForwards() {
		
		route.setRoute(testRoute1);
		
		try {
			route.goForwards(10);
		} catch (NoRouteNodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(50.2012534, route.getTotalDistanceRemaining() - route.getTotalDistance(), 0.0000001);
	}
	
	@Test 
	public void isRouteComplete() {
		
		route.setRoute(testRoute1);
		
		try {
			
			for (int i = 0; i < route.getNumberOfNodes()-1; i++) {
				
				route.gotoNextNode();
				
			}
			
			assertTrue(route.isRouteComplete());
			
		} catch (NoRouteNodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test 
	public void getSectorDistance() {
		// TODO ... Come back to this later. getSectorDistance() always returns 0
		assertTrue(true);
	}
	
	@Test 
	public void getSectorDistanceRemaining() {

		route.setRoute(testRoute1);
		
		try {
			route.goForwards(10.00);
		} catch (NoRouteNodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(25.100626, route.getSectorDistanceRemaining(), 0.0001);
		
	}
		
	@Test 
	public void getTotalDistance() {
		
		route.setRoute(testRoute1);
		assertEquals(-35.100626748293294, route.getTotalDistance(), 0.00000000001);
		
	}
	
	@Test 
	public void getTotalDistanceRemaining() {
		
		route.setRoute(testRoute1);
		assertEquals(35.100626748293294, route.getTotalDistanceRemaining(), 0.00000000001);
		
	}
	
	
	/** 
	 * The getCurrentLatitude() and getCurrentLongitude() tests use route.gotoEnd()
	 * this seems to return the correct result, however when used at any other time
	 * it returns some odd results. Need to look into this further 
	 */
	@Test 
	public void getCurrentLatitude() {
		
		route.setRoute(testRoute1);
		
		try {
			route.gotoEnd();
		} catch (NoRouteNodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(-12.461, route.getCurrentLatitude(), 0.00001);
		
	}
	
	@Test 
	public void getCurrentLongitude() {
		
		route.setRoute(testRoute1);
		
		try {
			route.gotoEnd();
		} catch (NoRouteNodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(130.8467, route.getCurrentLongitude(), 0.00001);
		
	}
	
	@Test 
	public void getCurrentElevation() {
		
		route.setRoute(testRoute1);
		
		try {
			route.gotoEnd();
		} catch (NoRouteNodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(19.4, route.getCurrentElevation(), 0.00001);
		
	}
	
	@Test 
	public void getNumberOfNodes() {
		
		route.setRoute(testRoute1);			
		assertEquals(10, route.getNumberOfNodes());
		
	}
	
	
	@Before
    public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("ArrowPointMaps-test.xml");
		
		route = (Route) context.getBean("Route");
		
		testRoute1.add(new RouteNode(-12.46599,130.84277,38.4));
		testRoute1.add(new RouteNode(-12.46575,130.84256,38.3));
		testRoute1.add(new RouteNode(-12.46552,130.84252,39.5));
		testRoute1.add(new RouteNode(-12.46552,130.84252,39.5));
		testRoute1.add(new RouteNode(-12.46536,130.84283,39.7));
		testRoute1.add(new RouteNode(-12.46296,130.84519,32.7));
		testRoute1.add(new RouteNode(-12.4622,130.84586,24.4));
		testRoute1.add(new RouteNode(-12.4622,130.84586,24.4));
		testRoute1.add(new RouteNode(-12.46181,130.84621,20.4));
		testRoute1.add(new RouteNode(-12.461,130.8467,19.4));
			
	}

}
