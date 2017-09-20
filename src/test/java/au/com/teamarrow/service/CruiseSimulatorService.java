package au.com.teamarrow.service;

import au.com.teamarrow.canbus.model.CanPacket;

import org.springframework.messaging.Message;

public interface CruiseSimulatorService {

    void processCanPacketMessage(Message<CanPacket> message);
    
    public int getVariationHertz();

	public void setVariationHertz(int variationHertz);

	public double getVelocityVariationPercent();

	public void setVelocityVariationPercent(double velocityVariationPercent);
	
	public void setActive(boolean active);
    
	
}



