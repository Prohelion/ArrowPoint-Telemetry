package au.com.teamarrow.model;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Test;

import au.com.teamarrow.utils.test.PropertyAsserter;


public class MeasurementTypeTest {

    @Test
    public void testGettersAndSetters() {
        PropertyAsserter.assertBasicGetterSetterBehavior(new MeasurementType());
    }

    @Test
    public void testSingleParameterConstructor() {
        MeasurementType mt = new MeasurementType(1L);
        assertEquals(1L, mt.getId());
    }
    
    @Test
    public void testSevenParameterConstructor() {
        MeasurementType mt = new MeasurementType(1L, "TestType", 0.0, 5.0, 15.0, 10.0, 1000);
        
        assertEquals(1L, mt.getId());
        assertEquals("TestType", mt.getType());
        assertEquals((Double)0.0, mt.getLowErrorThreshold());
        assertEquals((Double)5.0, mt.getLowWarningThreshold());
        assertEquals((Double)15.0, mt.getHighErrorThreshold());
        assertEquals((Double)10.0, mt.getHighWarningThreshold());
        assertEquals((Integer)1000, mt.getReportingFrequency());
    }
}
