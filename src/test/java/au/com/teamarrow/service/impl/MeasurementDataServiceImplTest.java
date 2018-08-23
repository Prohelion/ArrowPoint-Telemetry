package au.com.teamarrow.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.ArrayList;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import au.com.teamarrow.dao.MeasurementDataRepository;
import au.com.teamarrow.model.MeasurementData;

public class MeasurementDataServiceImplTest {

    @Mock
    private MeasurementDataRepository mMeasurementDataRepository;
    
    @Mock
    private Page<MeasurementData> mPage;
        
    @Mock
    private MeasurementData mMeasurementData;

    private MeasurementDataServiceImpl service;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);

        this.service = new MeasurementDataServiceImpl();
        ReflectionTestUtils.setField(this.service, "measurementDataRepository", this.mMeasurementDataRepository);
    }
    
    /*@Test
    public void testGetMeasurementsForDevice() {
        when(this.mMeasurementDataRepository.findByDataPointCanId(any(Integer.class), any(Pageable.class))).thenReturn(mPage);
        when(mPage.getContent()).thenReturn(new ArrayList<MeasurementData>());        
        verify(mMeasurementDataRepository).findByDataPointCanId(any(Integer.class), any(Pageable.class));
        verify(mPage).getContent();
        verifyNoMoreInteractions(mMeasurementDataRepository, mPage);
    }

    @Test
    public void testFindLatestDataForCanId() {
        when(this.mMeasurementDataRepository.findLatestDataForCanId(any(Integer.class))).thenReturn(new ArrayList<MeasurementData>());
        
        List<MeasurementData> result = service.findLatestDataForCanId(0x500);
        
        verify(mMeasurementDataRepository).findLatestDataForCanId(any(Integer.class));
        verifyNoMoreInteractions(mMeasurementDataRepository);
    }
    
   @Test
    public void testProcessMeasurementData() {
        when(this.mMessage.getPayload()).thenReturn(mMeasurementData);
        
        this.service.processMeasurementData(mMessage);
        verify(mMessage).getPayload();
        verify(mMeasurementDataRepository).save(mMeasurementData);
        verifyNoMoreInteractions(mMessage, mMeasurementDataRepository);
    } */
    
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    
    @Test
    public void testSignedVsUnsignedData() {
    	
    	assertEquals(Integer.valueOf("FA98",16).shortValue(),-1384);
    	assertEquals(Integer.valueOf("66",16).shortValue(),102);
    	assertEquals(Integer.valueOf("0020",16).shortValue(),32);
    	
    	byte[] testArray =  new byte[] {(byte)-99,(byte)-6};
    	
    	ArrayUtils.reverse(testArray);
    	
		byte[] sa = ArrayUtils.subarray(testArray, 0, 0 + 2);
        String hex = null;
		hex = bytesToHex(sa);
		
		Integer value = new Integer(Integer.valueOf(hex,16).shortValue());
    	
		assertEquals((Integer)value,new Integer(-1379));
    	
    }
    
    
    
}
