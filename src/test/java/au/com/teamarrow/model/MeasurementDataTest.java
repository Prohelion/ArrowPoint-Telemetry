package au.com.teamarrow.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.joda.time.DateTime;
import org.junit.Test;

import au.com.teamarrow.utils.test.PropertyAsserter;


public class MeasurementDataTest {

    @Test
    public void testGettersAndSetters() {
        PropertyAsserter.assertBasicGetterSetterBehavior(new MeasurementData());
    }

    @Test
    public void testNineParameterConstructor() {
        DateTime dt = DateTime.now();
        MeasurementData md = new MeasurementData(0x400, dt, false, false, 128, 0.0, 0, "0.0", "Okay");
        
        assertEquals((Integer)0x400, md.getDataPointCanId());
        assertEquals(dt, md.getTimestamp());
        assertFalse(md.getExtended());
        assertFalse(md.getRetry());
        assertEquals((Integer)128, md.getLength());
        assertEquals((Double)0.0, md.getFloatValue());
        assertEquals((Integer)0, md.getIntegerValue());
        assertEquals("0.0", md.getCharValue());
        assertEquals("Okay", md.getState());
    }
}
