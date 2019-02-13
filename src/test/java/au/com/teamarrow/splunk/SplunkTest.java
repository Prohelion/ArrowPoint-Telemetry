package au.com.teamarrow.splunk;

import static org.junit.Assert.*;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.splunk.event.SplunkEvent;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ContextConfiguration;
import com.splunk.Application;
import com.splunk.HttpService;
import com.splunk.SSLSecurityProtocol;
import com.splunk.Service;
import com.splunk.ServiceArgs;

import au.com.teamarrow.model.MeasurementData;

//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class SplunkTest {
	
	@Autowired
    @Qualifier("splunkData")
    MessageChannel splunkDataChannel;

	@Autowired
    @Qualifier("measurementAggregatedDataChannel")
    MessageChannel measurementAggregatedDataChannel;

	
	private static String DATA_KEY = "data";
	
	//@Test
	public void testSendMessageToSplunk() {
		
        HttpService.setSslSecurityProtocol(SSLSecurityProtocol.TLSv1_2);

		try {
			SplunkEvent data = new SplunkEvent();
			data.addPair(DATA_KEY, "Test");		  				
			splunkDataChannel.send(MessageBuilder.withPayload(data).build());
		} catch (Exception ex) {
			assertTrue(false);
		}
		
		assertTrue(true);
		
	}	
	
	//@Test
	public void testSendMeasurementDataToSplunk() {

        HttpService.setSslSecurityProtocol(SSLSecurityProtocol.TLSv1_2);

        OffsetDateTime dt = OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"));
        
		try {
			MeasurementData data = new MeasurementData(1234, dt, false, false, 16, (double)100, 200, "C", "Good");					  			
			measurementAggregatedDataChannel.send(MessageBuilder.withPayload(data).build());
		} catch (Exception ex) {
			assertTrue(false);
		}
		
		assertTrue(true);
		
	}

	
	//@Test
	public void loginToSplunk() {
	
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		
		SSLContext context = null;
		try {
			context = SSLContext.getInstance("TLS");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			context.init(null,null,null);
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SSLSocketFactory factory = (SSLSocketFactory)context.getSocketFactory();
		SSLSocket socket = null;
		try {
			socket = (SSLSocket)factory.createSocket();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] protocols = socket.getSupportedProtocols();

		System.out.println("Supported Protocols: " + protocols.length);
		for(int i = 0; i < protocols.length; i++)
		{
		     System.out.println(" " + protocols[i]);
		}

		protocols = socket.getEnabledProtocols();

		System.out.println("Enabled Protocols: " + protocols.length);
		for(int i = 0; i < protocols.length; i++)
		{
		     System.out.println(" " + protocols[i]);
		}
		
			//HttpService.setSslSecurityProtocol(SSLSecurityProtocol.SSLv3);
			
	        // Create a map of arguments and add login parameters
	        ServiceArgs loginArgs = new ServiceArgs();
	        loginArgs.setUsername("admin");
	        loginArgs.setPassword("***REMOVED***");
	        loginArgs.setHost("localhost");
	        loginArgs.setPort(8089);
//	        loginArgs.setSSLSecurityProtocol(SSLSecurityProtocol.TLSv1_2);
 	        HttpService.setSslSecurityProtocol(SSLSecurityProtocol.TLSv1_2);

	        // Create a Service instance and log in with the argument map
	        Service service = Service.connect(loginArgs);

	        // A second way to create a new Service object and log in
	        // Service service = new Service("localhost", 8089);
	        // service.login("admin", "changeme");

	        // A third way to create a new Service object and log in
	        // Service service = new Service(loginArgs);
	        // service.login();

	        // Print installed apps to the console to verify login
	        for (Application app : service.getApplications().values()) {
	            System.out.println(app.getName());
	        }
	 }
	
	
}
