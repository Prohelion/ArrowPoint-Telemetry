package com.prohelion.service;

import org.apache.commons.codec.DecoderException;

import com.prohelion.canbus.model.CanPacket;

public interface CanPacketBuilderService {
    CanPacket buildCanPacket(String[] csvLine) throws DecoderException;
}
