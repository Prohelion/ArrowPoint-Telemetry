package au.com.teamarrow.service;

import org.apache.commons.codec.DecoderException;

import au.com.teamarrow.canbus.model.CanPacket;

public interface CanPacketBuilderService {
    CanPacket buildCanPacket(String[] csvLine) throws DecoderException;
}
