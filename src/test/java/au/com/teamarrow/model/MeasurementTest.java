package au.com.teamarrow.model;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Test;
import org.mockito.Mock;

import au.com.teamarrow.utils.test.PropertyAsserter;


public class MeasurementTest {

    @Mock
    private DeviceType mDeviceType;
    
    @Mock
    private Device mDevice;
    
    @Test
    public void testGettersAndSetters() {
        PropertyAsserter.assertBasicGetterSetterBehavior(new Measurement());
    }

    @Test
    public void testSingleParameterConstructor() {
        Measurement m = new Measurement(1);
        assert(m.getId().intValue() == 1);
    }
    
    @Test
    public void testEightParameterConstructor() {
        Measurement m = new Measurement(1, mDeviceType, mDevice, "TestName", 0x400, 1000, "TestType", Collections.<DataPoint>emptySet());
        
        assert(m.getId().intValue() == 1);
        assertEquals(mDeviceType, m.getDeviceType());
        assertEquals(mDevice, m.getDevice());
        assertEquals("TestName", m.getName());
        assertEquals((Integer)0x400, m.getCanId());
        assertEquals((Integer)1000, m.getReportingFrequency());
        assertEquals("TestType", m.getType());
        assertEquals(Collections.<DataPoint>emptySet(), m.getDataPoints());
    }
}
