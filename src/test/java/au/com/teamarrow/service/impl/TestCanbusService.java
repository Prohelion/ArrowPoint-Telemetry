package au.com.teamarrow.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class TestCanbusService {

    @Autowired
    @Qualifier("output")
    MessageChannel mcInput;
    
    byte[] canbusData = new byte[] { 0x00, 0x54, 0x72, 0x69, 0x74, 0x69, 0x75, 0x6d, 0x00, 0x00, (byte)0xc5, (byte)0xc0, (byte)0xcf, (byte)0xc2, 0x50, 0x00, 0x00, 0x00, 0x05, 0x02, 0x00, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte)0x80, 0x3f, 0x00, 0x00, 0x05, 0x05, 0x00, 0x08, 0x32, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x04};
    byte[] cb2 = new byte[]        { 0x00, 0x54, 0x72, 0x69, 0x74, 0x69, 0x75, 0x6d, 0x00, 0x00, (byte)0xc5, (byte)0xc0, (byte)0xcf, (byte)0xc2, 0x50, 0x00, 0x00, 0x00, 0x05, 0x01, 0x00, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
    byte[] cb3 = new byte[]        { 0x00, 0x54, 0x72, 0x69, 0x74, 0x69, 0x75, 0x6d, 0x00, 0x00, (byte)0xc5, (byte)0xc0, (byte)0xcf, (byte)0xc2, 0x50, 0x00, 0x00, 0x00, 0x05, 0x00, 0x00, 0x08, 0x01, 0x00, 0x00, 0x00, 0x36, 0x38, 0x30, 0x54 };
    
								    /*
								     * Packet structure is:
								     * 
								     * +-+------------------+-+----------------------+--------------+---------+----------+---------+
								     * |8	|56 - Bus Identifer						|8		|56 - Client Identifier											|32 - Identifer			|8 - Flags
								     * 																																						|8 - Length
								     * 																																							|64 - Data|
								     * +-+------------------+-+----------------------+--------------+---------+----------+---------+
								     * 
								     */								
    
    
    @Test
    public void test() throws Exception {
        mcInput.send(MessageBuilder.withPayload(canbusData).build());
    }
    
    @Test
    public void testShorter() throws Exception {
        mcInput.send(MessageBuilder.withPayload(cb2).build());
    }
    
    @Test
    public void testCB3() throws Exception {
        mcInput.send(MessageBuilder.withPayload(cb3).build());
    }
}
