package au.com.teamarrow.splunk;

import com.splunk.HttpService;
import com.splunk.SSLSecurityProtocol;

public class ProtocolSetter {
	
	public ProtocolSetter() {
        HttpService.setSslSecurityProtocol(SSLSecurityProtocol.TLSv1_2);
	}

}
