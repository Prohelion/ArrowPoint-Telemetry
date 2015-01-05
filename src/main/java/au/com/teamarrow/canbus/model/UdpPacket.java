package au.com.teamarrow.canbus.model;

import java.util.ArrayList;

import org.apache.commons.codec.binary.Hex;

public class UdpPacket {

    private byte length;
    private byte[] data;
    private byte[] busId;
    private byte[] senderId;
    private ArrayList<CanPacket> canPackets;
    
    public UdpPacket() {
    	
    }
    
    public UdpPacket(byte length, byte[] data, byte[] busId, byte[] senderId) {
        this.length = length;
        this.data = data;
        this.busId = busId;
        this.senderId = senderId;
    }

    public ArrayList<CanPacket> getCanPackets() {
        return canPackets;
    }

    public void setCanPackets(ArrayList<CanPacket> canPacketList) {
        this.canPackets = canPacketList;
    }

    @Override
    public String toString() {
    	
    	// These can be null so the logic attempt to avoid the encoding step in that case
    	String busIdStr = new String("null");
    	String senderIdStr = new String("null");
    	
    	if ( busId != null )  busIdStr = Hex.encodeHexString(busId);
    	if ( senderId != null) senderIdStr = Hex.encodeHexString(senderId);
    	
        return "UdpPacket [busId=" + busIdStr + ", senderId=" + senderIdStr + ", canPackets=" + canPackets + "]";
    }
}
