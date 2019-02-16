package com.prohelion.model;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Test;

import com.prohelion.model.DeviceType;
import com.prohelion.model.Measurement;

import com.prohelion.utils.test.PropertyAsserter;


public class DeviceTypeTest {

    @Test
    public void testGettersAndSetters() {
        PropertyAsserter.assertBasicGetterSetterBehavior(new DeviceType());
    }

    @Test
    public void testSingleParameterConstructor() {
        DeviceType dt = new DeviceType(1L);
        assertEquals(1L, dt.getId());
    }
    
    @Test
    public void testThreeParameterConstructor() {
        DeviceType dt = new DeviceType(1L, "TestType", Collections.<Measurement>emptySet());
        
        assertEquals(1L, dt.getId());
        assertEquals("TestType", dt.getType());
        assertEquals(Collections.<Measurement>emptySet(), dt.getMeasurements());
    }
}
