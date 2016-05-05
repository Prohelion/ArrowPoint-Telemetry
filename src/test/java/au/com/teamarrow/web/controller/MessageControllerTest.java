package au.com.teamarrow.web.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class MessageControllerTest {
	
	FleetController messageController = new FleetController();
	
	@Test
	public void testSendMessage() {		
		messageController.sendMessage("HelloWorld");
	}	
	
}
