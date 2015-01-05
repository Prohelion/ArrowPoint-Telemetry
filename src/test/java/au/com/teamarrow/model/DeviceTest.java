package au.com.teamarrow.model;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Test;

import au.com.teamarrow.utils.test.PropertyAsserter;


public class DeviceTest {

    @Test
    public void testGettersAndSetters() {
        PropertyAsserter.assertBasicGetterSetterBehavior(new Device());
    }

    @Test
    public void testSingleParameterConstructor() {
        Device d = new Device(1L);
        assertEquals(1L, d.getId());
    }
    
    @Test
    public void testFourParameterConstructor() {
        Device d = new Device(1L, "Test", "TST", Collections.<Measurement>emptySet());
        
        assertEquals(1L, d.getId());
        assertEquals("Test", d.getName());
        assertEquals("TST", d.getAbbreviation());
        assertEquals(Collections.<Measurement>emptySet(), d.getMeasurements());
    }
}
