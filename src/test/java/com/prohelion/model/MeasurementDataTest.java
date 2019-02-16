package com.prohelion.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import org.junit.Test;

import com.prohelion.model.MeasurementData;

public class MeasurementDataTest {

    @Test
    public void testNineParameterConstructor() {
    	OffsetDateTime dt = OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"));

        MeasurementData md = new MeasurementData(0x400, dt, false, false, 128, 0.0, 0, "0.0", "Normal");
        
        assertEquals((Integer)0x400, md.getDataPointCanId());
        assertEquals(dt, md.getTimestamp());
        assertFalse(md.getExtended());
        assertFalse(md.getRetry());
        assertEquals((Integer)128, md.getLength());
        assertEquals((Double)0.0, md.getFloatValue());
        assertEquals((Integer)0, md.getIntegerValue());
        assertEquals("0.0", md.getCharValue());
        assertEquals("Normal", md.getState());
    }
}
