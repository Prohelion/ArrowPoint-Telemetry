package au.com.teamarrow.alerts;

import static org.junit.Assert.*;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.splunk.event.SplunkEvent;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.com.teamarrow.model.MeasurementData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class AlertsTest {
		
	@Autowired
    @Qualifier("AlertManager")
    AlertManager alertManager;
		
	@Test
	public void testLoadAlertFileCheckNormal() {		
		assertTrue(alertManager.getCurrentAlertLevel() == AlertData.NORMAL);		
	}	
	

	@Test
	public void testLoadAlertFileCheckAlertLow() {
		
		alertManager.setDataPoint(28544, (double)3000);
		assertTrue(alertManager.getCurrentAlertLevel() == AlertData.NORMAL);

		alertManager.setDataPoint(28544, (double)2890);
		assertTrue(alertManager.getCurrentAlertLevel() == AlertData.WARNING);
		
		alertManager.setDataPoint(28544, (double)2700);
		assertTrue(alertManager.getCurrentAlertLevel() == AlertData.ALERT);
				
		alertManager.setDataPoint(28544, (double)2590);
		assertTrue(alertManager.getCurrentAlertLevel() == AlertData.SHUTDOWN);
		
		alertManager.setDataPoint(28544, (double)3000);
		assertTrue(alertManager.getCurrentAlertLevel() == AlertData.NORMAL);
		
	}	
	

	@Test
	public void testLoadAlertFileCheckAlertHigh() {
		
		alertManager.setDataPoint(28562, (double)40);
		assertTrue(alertManager.getCurrentAlertLevel() == AlertData.NORMAL);

		alertManager.setDataPoint(28562, (double)56);
		assertTrue(alertManager.getCurrentAlertLevel() == AlertData.WARNING);
		
		alertManager.setDataPoint(28562, (double)61);
		assertTrue(alertManager.getCurrentAlertLevel() == AlertData.ALERT);
				
		alertManager.setDataPoint(28562, (double)66);
		assertTrue(alertManager.getCurrentAlertLevel() == AlertData.SHUTDOWN);
		
		alertManager.setDataPoint(28562, (double)40);
		assertTrue(alertManager.getCurrentAlertLevel() == AlertData.NORMAL);	
	}	
	
	
	@Test
	public void testFlags() {		
					
		try {
			alertManager.setSupressionDelay(0);
			
			alertManager.setDataPoint(0x4012, (double)2);
			assertTrue(alertManager.getCurrentAlertLevel() == AlertData.ALERT);
			
			alertManager.setDataPoint(0x4012, (double)0);
			assertTrue(alertManager.getCurrentAlertLevel() == AlertData.NORMAL);
	
			alertManager.setDataPoint(0x4012, (double)2);
			assertTrue(alertManager.getCurrentAlertLevel() == AlertData.ALERT);
	
			alertManager.setDataPoint(0x4012, (double)4);
			assertTrue(alertManager.getCurrentAlertLevel() == AlertData.ALERT);
	
			alertManager.setDataPoint(0x4012, (double)6);
			assertTrue(alertManager.getCurrentAlertLevel() == AlertData.ALERT);
	
			alertManager.setDataPoint(0x4012, (double)0);
			assertTrue(alertManager.getCurrentAlertLevel() == AlertData.NORMAL);
			
			alertManager.setDataPoint(0x5056, (double)1);
			assertTrue(alertManager.getCurrentAlertLevel() == AlertData.ALERT);			
			
			alertManager.setDataPoint(0x4012, (double)6);
			assertTrue(alertManager.getCurrentAlertLevel() == AlertData.ALERT);
			alertManager.triggerAlertScripts();			
			Thread.sleep(3000);

			alertManager.setDataPoint(0x4012, (double)0);
			alertManager.setDataPoint(0x5056, (double)0);			
			assertTrue(alertManager.getCurrentAlertLevel() == AlertData.NORMAL);
			alertManager.triggerAlertScripts();			
			Thread.sleep(3000);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	@Test
	public void testLights() {
		String results = alertManager.executeCommand("C:/TeamArrow/alerts/StatusNormal.bat");
		
		results = results + "";
		
	}
	
	
	@Test
	public void testAlertScripts() {
					
		try {
		
			alertManager.setSupressionDelay(0);
			
			alertManager.setDataPoint(28562, (double)40);
			assertTrue(alertManager.getCurrentAlertLevel() == AlertData.NORMAL);
			alertManager.triggerAlertScripts();
			
			Thread.sleep(3000);
			
			alertManager.setDataPoint(28562, (double)56);
			assertTrue(alertManager.getCurrentAlertLevel() == AlertData.WARNING);
			alertManager.triggerAlertScripts();
			
			Thread.sleep(3000);
			
			alertManager.setDataPoint(28562, (double)61);
			assertTrue(alertManager.getCurrentAlertLevel() == AlertData.ALERT);
			alertManager.triggerAlertScripts();
					
			Thread.sleep(3000);
			
			alertManager.setDataPoint(28562, (double)66);
			assertTrue(alertManager.getCurrentAlertLevel() == AlertData.SHUTDOWN);
			alertManager.triggerAlertScripts();
			
			Thread.sleep(3000);
			
			alertManager.setDataPoint(28562, (double)40);
			assertTrue(alertManager.getCurrentAlertLevel() == AlertData.NORMAL);
			alertManager.triggerAlertScripts();
						
			Thread.sleep(3000);
		} catch (Exception ex) {
			assertTrue(false);
		}
			
	}
	
}
