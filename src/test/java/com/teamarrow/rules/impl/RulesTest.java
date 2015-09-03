package com.teamarrow.rules.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import au.com.teamarrow.rules.Rule;
import au.com.teamarrow.rules.impl.CanAvailabilityCheck;
import au.com.teamarrow.rules.impl.CanStatusCheck;
import au.com.teamarrow.rules.impl.PingComponent;
import au.com.teamarrow.scheduled.impl.PingRulesEngineWorker;


public class RulesTest {

	String successMessage = new String("Success Message");
	String failureMessage = new String("Failure Message");
		
	//@Test
	public void testCanAvailabilityCheck() {
		/*
		MeasurementDataServiceStub measurementDataServiceStub = new MeasurementDataServiceStub();

		CanAvailabilityCheck canCheck = new CanAvailabilityCheck("Check Can Availability", 1808,500, failureMessage, successMessage);
		canCheck.setMeasurementDataService(measurementDataServiceStub);		
		
		canCheck.execute();
		
		assertEquals(canCheck.isFailed(),false);
		assertEquals(canCheck.isStateChanged(),false);		
		assertEquals(canCheck.getMessage(), successMessage);
		
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		canCheck.execute();
		
		assertEquals(canCheck.isFailed(),true);
		assertEquals(canCheck.isStateChanged(),true);		
		assertEquals(canCheck.getMessage(), failureMessage);		
		*/
	}

	
	//@Test
	public void testCanStatusCheck() {
		
		/*MeasurementDataServiceStub measurementDataServiceStub = new MeasurementDataServiceStub();

		// In range
		CanStatusCheck canCheck = new CanStatusCheck("Check Can1", 1808, (double)0, (double)80, failureMessage, successMessage);
		canCheck.setMeasurementDataService(measurementDataServiceStub);		
		
		canCheck.execute();
		
		assertEquals(canCheck.isFailed(),false);
		assertEquals(canCheck.isStateChanged(),false);		
		assertEquals(canCheck.getMessage(), successMessage);
		
		// Over max
		canCheck = new CanStatusCheck("Check Can2", 1808, (double)0, (double)50, failureMessage, successMessage);
		canCheck.setMeasurementDataService(measurementDataServiceStub);		
		
		canCheck.execute();
		
		assertEquals(canCheck.isFailed(),true);
		assertEquals(canCheck.isStateChanged(),true);		
		assertEquals(canCheck.getMessage(), failureMessage);		
		
		// Under min
		canCheck = new CanStatusCheck("Check Can3", 1808, (double)80, (double)100, failureMessage, successMessage);
		canCheck.setMeasurementDataService(measurementDataServiceStub);		
		
		canCheck.execute();
		
		assertEquals(canCheck.isFailed(),true);
		assertEquals(canCheck.isStateChanged(),true);		
		assertEquals(canCheck.getMessage(), failureMessage);		
*/
		
		
	}

	
	
	@Test
	public void testRulesWorker() {
		
		PingRulesEngineWorker rulesEngineWorker = new PingRulesEngineWorker();
		
		List<Rule> rules = new ArrayList<Rule>();
				
		rules.add(new PingComponent("Printer", "192.168.14.91", "Printer is not available", "Printer is active"));

		rulesEngineWorker.setRules(rules);

		// Use this option
		rulesEngineWorker.work();		
		// or this option
		/*for (int i=0; i<100; i++) {
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
	}
	
	@Test
	public void testPingComponent() {
		
		PingComponent workingPingComponent = new PingComponent("Test","127.0.0.1",failureMessage,successMessage);
		
		workingPingComponent.execute();
		
		assertEquals(workingPingComponent.isFailed(),false);
		assertEquals(workingPingComponent.isStateChanged(),false);		
		assertEquals(workingPingComponent.getMessage(), successMessage);
					
		PingComponent failingPingComponent = new PingComponent("Test","0.0.0.0",failureMessage,successMessage);
		
		failingPingComponent.execute();
		
		assertEquals(failingPingComponent.isFailed(),true);
		assertEquals(failingPingComponent.isStateChanged(),true);		
		assertEquals(failingPingComponent.getMessage(), failureMessage);
		
	
		
		
	}
	
}
