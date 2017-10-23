package au.com.teamarrow.service.impl;

import static org.junit.Assert.*;

import java.util.List;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import au.com.teamarrow.model.MeasurementData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class MeasurementDataEnrichmentImplTest {
	
	@Autowired
    @Qualifier("enricherBean")
	MeasurementDataEnrichment enrichment;	
	
	@Test
    public void testEnrichment() throws Exception {
				/*
		List<MeasurementData> results = null;
		
		MeasurementData array1Amps = new MeasurementData(0x7014,new DateTime(), false, false, 8, (double)0, 765, "", "");
		MeasurementData array2Amps = new MeasurementData(0x7054,new DateTime(), false, false, 8, (double)0, 765, "", "");
		MeasurementData array3Amps = new MeasurementData(0x7094,new DateTime(), false, false, 8, (double)0, 765, "", "");
		
		MeasurementData array1Volts = new MeasurementData(0x2FA0,new DateTime(), false, false, 8, (double)0, 88312, "", "");
		MeasurementData array2Volts = new MeasurementData(0x2FA0,new DateTime(), false, false, 8, (double)0, 88312, "", "");
		MeasurementData array3Volts = new MeasurementData(0x2FA0,new DateTime(), false, false, 8, (double)0, 88312, "", "");
		
		MeasurementData busCurrent = new MeasurementData(0x4024,new DateTime(), false, false, 8, (double)49.480, 0, "", "");
		MeasurementData busVolts = new MeasurementData(0x4020,new DateTime(), false, false, 8, (double)148.27, 0, "", "");
					
		// Insert array1Amps
		results = enrichment.enrichMeasurementDataMessages(MessageBuilder.withPayload(array1Amps).build());
		assertEquals(results.size(),1);				

		// Insert array1Volts
		results = enrichment.enrichMeasurementDataMessages(MessageBuilder.withPayload(array1Volts).build());
		assertEquals(results.size(),2);	
		assertTrue((double)results.get(1).getFloatValue() == (double)67.55868);
		assertTrue((double)results.get(1).getDataPointCanId() == (double)0x3410);

		// Insert array2Amps
		results = enrichment.enrichMeasurementDataMessages(MessageBuilder.withPayload(array2Amps).build());
		assertEquals(results.size(),1);				

		// Insert array2Volts
		results = enrichment.enrichMeasurementDataMessages(MessageBuilder.withPayload(array2Volts).build());
		assertEquals(results.size(),2);	
		assertTrue((double)results.get(1).getFloatValue() == (double)67.55868);
		assertTrue((double)results.get(1).getDataPointCanId() == (double)0x3420);
		
		// Insert array3Amps
		results = enrichment.enrichMeasurementDataMessages(MessageBuilder.withPayload(array3Amps).build());
		assertEquals(results.size(),1);				

		// Insert array3Volts
		results = enrichment.enrichMeasurementDataMessages(MessageBuilder.withPayload(array3Volts).build());
		assertEquals(results.size(),3);	
		assertTrue((double)results.get(1).getFloatValue() == (double)67.55868);
		assertTrue((double)results.get(1).getDataPointCanId() == (double)0x3430);

		// Assert total array power calcs
		assertTrue((double)results.get(2).getFloatValue() == (double)202.67604);
		assertTrue((double)results.get(2).getDataPointCanId() == (double)0x3440);


		// Insert busCurrent
		results = enrichment.enrichMeasurementDataMessages(MessageBuilder.withPayload(busCurrent).build());
		assertEquals(results.size(),1);				

		// Insert busVolts
		results = enrichment.enrichMeasurementDataMessages(MessageBuilder.withPayload(busVolts).build());
		assertEquals(results.size(),3);	
		assertTrue((double)results.get(1).getFloatValue() == (double)7336.3996);
		assertTrue((double)results.get(1).getDataPointCanId() == (double)0x3450);

		// Net power position
		assertTrue((double)results.get(2).getFloatValue() == (double)(202.67604 - 7336.3996));
		assertTrue((double)results.get(2).getDataPointCanId() == (double)0x3460);		
		*/
    }
    
	
}
