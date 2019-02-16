package com.prohelion.splunk;

import com.splunk.HttpService;
import com.splunk.SSLSecurityProtocol;

public class ProtocolSetter {
	
	public ProtocolSetter() {
        HttpService.setSslSecurityProtocol(SSLSecurityProtocol.TLSv1_2);
	}

}
