package au.com.teamarrow.service;

import org.springframework.integration.Message;

import au.com.teamarrow.canbus.model.CanPacket;

public interface CanbusService {

    void processUdpPacketMessage(Message<CanPacket> message);
}
