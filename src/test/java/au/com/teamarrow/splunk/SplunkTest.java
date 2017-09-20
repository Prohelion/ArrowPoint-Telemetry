package au.com.teamarrow.splunk;

import static org.junit.Assert.*;

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
public class SplunkTest {
	
	@Autowired
    @Qualifier("splunkData")
    MessageChannel splunkDataChannel;

	@Autowired
    @Qualifier("measurementAggregatedDataChannel")
    MessageChannel measurementAggregatedDataChannel;

	
	private static String DATA_KEY = "data";
	
	@Test
	public void testSendMessageToSplunk() {

		try {
			SplunkEvent data = new SplunkEvent();
			data.addPair(DATA_KEY, "Test");		  				
			splunkDataChannel.send(MessageBuilder.withPayload(data).build());
		} catch (Exception ex) {
			assertTrue(false);
		}
		
		assertTrue(true);
		
	}	
	
	@Test
	public void testSendMeasurementDataToSplunk() {

		try {
			MeasurementData data = new MeasurementData(1234, new DateTime(), false, false, 16, (double)100, 200, "C", "Good");					  			
			measurementAggregatedDataChannel.send(MessageBuilder.withPayload(data).build());
		} catch (Exception ex) {
			assertTrue(false);
		}
		
		assertTrue(true);
		
	}


	
}
