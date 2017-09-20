package au.com.teamarrow.canbus.model;

import java.nio.ByteBuffer;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.codec.binary.Hex;
import org.joda.time.DateTime;

import au.com.teamarrow.contrib.jodatime.binding.DateTimeXmlAdapter;

@XmlRootElement(name = "CanPacket")
public class CanPacket {
    
    private byte[] id;
    private Integer idBase10;
    private DateTime timestamp;
    private boolean extended;
    private boolean rtr;
    private byte length;
    private byte[] data;
    
    public CanPacket() {
        
    }
        
    
    public CanPacket(byte[] id, boolean extended, boolean rtr, byte length, byte[] data) {
        this.timestamp = new DateTime();
        this.id = id;
        
        this.idBase10 = ByteBuffer.wrap(id).getInt();               
        this.extended = extended;
        this.rtr = rtr;
        this.length = length;
        this.data = data;
    }

    
    public CanPacket(int base10id, boolean extended, boolean rtr, byte length, float segmentOne, float segmentTwo) {
    	byte[] id = ByteBuffer.allocate(4).putInt(base10id).array();
    	byte[] segmentOneBytes = ByteBuffer.allocate(4).put(floatToBytes(segmentOne)).array();
    	byte[] segmentTwoBytes = ByteBuffer.allocate(4).put(floatToBytes(segmentTwo)).array();
    	byte[] data = new byte[8];

    	byte[] segmentOneInvert = new byte[4];
    	byte[] segmentTwoInvert = new byte[4];
    	
 		// Invert the data because our significant bit is opposite
 		for (int i = 0; i<=3; i++)	{
 			segmentOneInvert[i] = segmentOneBytes[3-i];
 			segmentTwoInvert[i] = segmentTwoBytes[3-i];
 		}
 		    	
    	System.arraycopy(segmentTwoInvert,0,data,0,segmentTwoInvert.length);
    	System.arraycopy(segmentOneInvert,0,data,segmentTwoInvert.length,segmentOneInvert.length);

        this.timestamp = new DateTime();
        this.id = id;
        
        this.idBase10 = base10id;               
        this.extended = extended;
        this.rtr = rtr;
        this.length = length;
        this.data = data;    	
    	
    }
    
    
    public CanPacket(int base10id, float segmentOne, float segmentTwo) {
    	byte[] id = ByteBuffer.allocate(4).putInt(base10id).array();
    	byte[] segmentOneBytes = ByteBuffer.allocate(4).put(floatToBytes(segmentOne)).array();
    	byte[] segmentTwoBytes = ByteBuffer.allocate(4).put(floatToBytes(segmentTwo)).array();
    	byte[] data = new byte[8];
    	
    	byte[] segmentOneInvert = new byte[4];
    	byte[] segmentTwoInvert = new byte[4];
    	
 		// Invert the data because our significant bit is opposite
 		for (int i = 0; i<=3; i++)	{
 			segmentOneInvert[i] = segmentOneBytes[3-i];
 			segmentTwoInvert[i] = segmentTwoBytes[3-i];
 		}
 		    	
    	System.arraycopy(segmentTwoInvert,0,data,0,segmentTwoInvert.length);
    	System.arraycopy(segmentOneInvert,0,data,segmentTwoInvert.length,segmentOneInvert.length);

        this.timestamp = new DateTime();
        this.id = id;
        
        this.idBase10 = base10id;               
        this.extended = false;
        this.rtr = false;
        this.length = 8;
        this.data = data;    	    	
    }
    
    
	@XmlAttribute(name = "id")
    public int getIdBase10() {
        return idBase10;
    }
    
    public void setIdBase10(byte[] id) {
        this.idBase10 = ByteBuffer.wrap(id).getInt();
    }
  
    @XmlTransient
    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }
  
    @XmlAttribute(name="time")
    @XmlJavaTypeAdapter(DateTimeXmlAdapter.class)
    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    @XmlAttribute
    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    @XmlAttribute
    public boolean isRtr() {
        return rtr;
    }

    public void setRtr(boolean rtr) {
        this.rtr = rtr;
    }

    @XmlAttribute
    public byte getLength() {
        return length;
    }

    public void setLength(byte length) {
        this.length = length;
    }

    @XmlTransient
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
    
    @XmlAttribute(name = "baseId")
    public Integer getBaseId() {
        return (idBase10 >>> 5) << 5; 
    }
    
    @XmlAttribute(name = "offset")
    public Integer getOffset() {
        return idBase10 & 0xff; 
    }
    
    @XmlAttribute(name = "dp1")
    public float getDataSegmentOne() {
        int intbits = (data[0] & 0xFF) 
            | ((data[1] & 0xFF) << 8) 
            | ((data[2] & 0xFF) << 16) 
            | ((data[3] & 0xFF) << 24);
        return Float.intBitsToFloat(intbits);
    }
    
    @XmlAttribute(name = "dp2")
    public float getDataSegmentTwo() {
        int intbits = (data[4] & 0xFF) 
            | ((data[5] & 0xFF) << 8) 
            | ((data[6] & 0xFF) << 16) 
            | ((data[7] & 0xFF) << 24);
        return Float.intBitsToFloat(intbits);
    }
    
        
    public int getDataSegmentOneInt() {
        int intbits = (data[0] & 0xFF) 
            | ((data[1] & 0xFF) << 8) 
            | ((data[2] & 0xFF) << 16) 
            | ((data[3] & 0xFF) << 24);
        return intbits;
    }
    
    public int getDataSegmentTwoInt() {
        int intbits = (data[4] & 0xFF) 
            | ((data[5] & 0xFF) << 8) 
            | ((data[6] & 0xFF) << 16) 
            | ((data[7] & 0xFF) << 24);
        return intbits;
    }    
    

    public int getDataSegmentTwoShortInt() {
        int intbits = (data[4] & 0xFF) 
            | ((data[5] & 0xFF) << 8);
        return intbits;
    }    

    public int getDataSegmentTwoByteSevenInt() {
        int intbits = ((data[7] & 0xFF) << 24);
        return intbits;
    }    
    
    @SuppressWarnings("unused")
	private float bytesToFloat(byte[] data) {
    	int intbits = (data[0] & 0xFF) 
                | ((data[1] & 0xFF) << 8) 
                | ((data[2] & 0xFF) << 16) 
                | ((data[3] & 0xFF) << 24);
            return Float.intBitsToFloat(intbits);
    }
    
    private byte[] floatToBytes(float data) {
    	int intbits = Float.floatToIntBits(data);
    	
    	 return new byte[] {
    		        (byte) (intbits >> 24),
    		        (byte) (intbits >> 16),
    		        (byte) (intbits >> 8),
    		        (byte) intbits};
    }
    
    
    @Override
    public String toString() {
        return String.format("CanPacket [id=%s, extended=%s, rtr=%s, length=%s, data=[%f, %f]]", 
            Hex.encodeHexString(id), extended, rtr, length, getDataSegmentOne(), getDataSegmentTwo());
    }
}
