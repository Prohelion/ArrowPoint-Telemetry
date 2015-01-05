package au.com.teamarrow.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import au.com.teamarrow.dao.MeasurementDataRepository;
import au.com.teamarrow.model.MeasurementData;
import org.springframework.integration.Message;


public class MeasurementDataServiceImplTest {

    @Mock
    private MeasurementDataRepository mMeasurementDataRepository;
    
    @Mock
    private Page<MeasurementData> mPage;
    
    @Mock
    private Message mMessage;
    
    @Mock
    private MeasurementData mMeasurementData;

    private MeasurementDataServiceImpl service;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);

        this.service = new MeasurementDataServiceImpl();
        ReflectionTestUtils.setField(this.service, "measurementDataRepository", this.mMeasurementDataRepository);
    }
    
    @Test
    public void testGetMeasurementsForDevice() {
        when(this.mMeasurementDataRepository.findByDataPointCanId(any(Integer.class), any(Pageable.class))).thenReturn(mPage);
        when(mPage.getContent()).thenReturn(new ArrayList<MeasurementData>());
        
        List<MeasurementData> result = service.getMeasurementsForDevice(0x400);
        
        verify(mMeasurementDataRepository).findByDataPointCanId(any(Integer.class), any(Pageable.class));
        verify(mPage).getContent();
        verifyNoMoreInteractions(mMeasurementDataRepository, mPage);
    }

    /*@Test
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
}
