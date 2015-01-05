package au.com.teamarrow.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import au.com.teamarrow.utils.test.PropertyAsserter;


public class DataPointTest {

    @Mock
    private Measurement mMeasurement;
    
    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testGettersAndSetters() {
        PropertyAsserter.assertBasicGetterSetterBehavior(new DataPoint());
    }

    @Test
    public void testTwoParameterConstructor() {
        DataPoint dp = new DataPoint(1L, mMeasurement);
        assertEquals(1L, dp.getId());
        assertEquals(mMeasurement, dp.getMeasurement());
    }
    
    @Test
    public void testSevenParameterConstructor() {
        DataPoint dp = new DataPoint(1L, mMeasurement, 0x400, "TestName", "TestDescription", 128, 2, "TestType", 0.0, 5.0, 10.0, 15.0, "m/s");
        
        assertEquals(1L, dp.getId());
        assertEquals(mMeasurement, dp.getMeasurement());
        assertEquals((Integer)0x400, dp.getDataPointCanId());
        assertEquals("TestName", dp.getName());
        assertEquals("TestDescription", dp.getDescription());
        assertEquals((Integer)128, dp.getLength());
        assertEquals((Integer)2, dp.getDataOffsetPosition());
        assertEquals("TestType", dp.getType());
        assertEquals((Double)0.0, dp.getLowErrorThreshold());
        assertEquals((Double)5.0, dp.getLowWarningThreshold());
        assertEquals((Double)10.0, dp.getHighWarningThreshold());
        assertEquals((Double)15.0, dp.getHighErrorThreshold());
        assertEquals("m/s", dp.getUnits());
    }
}
