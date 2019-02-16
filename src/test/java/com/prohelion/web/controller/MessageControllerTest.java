package com.prohelion.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.prohelion.service.FleetService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class MessageControllerTest {
	
	@Autowired
	FleetService fleetService;
	
	@Test
	public void testSendMessage() {		
		fleetService.sendMessage("HelloWorld");
	}	
	
}
