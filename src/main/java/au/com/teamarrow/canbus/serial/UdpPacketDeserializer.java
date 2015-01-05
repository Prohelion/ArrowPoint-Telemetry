package au.com.teamarrow.canbus.serial;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.BitSet;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.serializer.Deserializer;
import org.springframework.stereotype.Component;

import au.com.teamarrow.canbus.model.CanPacket;
import au.com.teamarrow.canbus.model.UdpPacket;

@Component
public class UdpPacketDeserializer implements Deserializer<UdpPacket> {

	private static final Logger CANLOG = LoggerFactory.getLogger("CanLog");
	
    public UdpPacket deserialize(InputStream inputStream) throws IOException {
        byte[] b = new byte[512];
        
        DataInputStream dis = new DataInputStream(inputStream);
        int nread, total = 0;
        while ((nread = dis.read(b)) >= 0) {
            total += nread;
        }
        
        /*
         * Packet structure is:
         * 
         * +-+------------------+-+----------------------+--------------+---------+----------+---------+
         * |8|56 - Bus Identifer|8|56 - Client Identifier|32 - Identifer|8 - Flags|8 - Length|64 - Data|
         * +-+------------------+-+----------------------+--------------+---------+----------+---------+
         * 
         */
        byte[] datastream = ArrayUtils.subarray(b, 0, total);
        
        // First byte is padding
        byte[] busIdentifier = ArrayUtils.subarray(datastream, 1, 8);
        byte[] clientIdentifier = ArrayUtils.subarray(datastream, 9, 16);
        
        ArrayList<CanPacket> packets = new ArrayList<CanPacket>();
        
        boolean done = false;
        int start = 16;
        boolean isBridgeHeartbeat = false;
        while (!done) {
            byte[] cpId = ArrayUtils.subarray(datastream, start, start + 4); // 17 - 20
            
            BitSet flags = BitSet.valueOf(ByteBuffer.wrap(ArrayUtils.subarray(datastream, start + 4, start + 5)));
            
            isBridgeHeartbeat = flags.get(7); // heartbeat flag
            boolean isSettingsPacket = flags.get(6);
            boolean isRtr = flags.get(1);
            boolean isExtended = flags.get(0);
            
            byte length = datastream[start + 5];
            byte[] data = ArrayUtils.subarray(datastream, start + 6, start + 6 + length);

            CanPacket cp = new CanPacket(cpId, isExtended, isRtr, length, data);
            
            // Log the receipt of this Can Packet
            CANLOG.info("0x" + Hex.encodeHexString(cp.getId()).substring(length-3) + " \t, 0x" +  Hex.encodeHexString(cp.getData()) + " \t\t, " + cp.getDataSegmentOne() + " \t\t, " + cp.getDataSegmentTwo() + " \t, 127.0.0.1");
                        
            packets.add(cp);
            
            if (start + 6 + length >= total) {
                done = true;
            } else {
                start += 6 + length;
            }
        }
        
        UdpPacket up = new UdpPacket((byte) total, ArrayUtils.subarray(b, 0, total), busIdentifier, clientIdentifier);
        up.setCanPackets(packets);

        return up;
    }
}
