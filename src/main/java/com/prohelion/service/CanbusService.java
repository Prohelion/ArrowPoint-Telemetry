package com.prohelion.service;

import org.springframework.messaging.Message;

import com.prohelion.canbus.model.CanPacket;

public interface CanbusService {

    void processUdpPacketMessage(Message<CanPacket> message);
}
