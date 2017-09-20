package au.com.teamarrow.utils.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import au.com.teamarrow.canbus.model.CanPacket;
import au.com.teamarrow.canbus.model.UdpPacket;

@Component
@ContextConfiguration(locations = {"/spring-test.xml"})
@Service
public class LogReplayer {

	
	//	can bus globals
	@Autowired
	@Qualifier("canbusOutputChannel")
	MessageChannel mcInput;

    @Autowired
   	private CarTestUtils carTestUtils;
	
    static CanPacket canPacket;
    static UdpPacket udpPacket = new UdpPacket();	
	
    public static void main (String[] arg) {
    	
    	ConfigurableApplicationContext context =  new ClassPathXmlApplicationContext("spring-test.xml");
    	LogReplayer logReplayer = context.getBean(LogReplayer.class);
    	logReplayer.start(arg);   
    	context.close();
    	    	
    }

    
    
	private void start (String[] arg) {
		
		String line = "";
		String cvsSplitBy = ",";
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
		
		System.out.println("TeamArrow Log Replayer V1.0");
		System.out.println("Note: Filenames should entered using Java form");
		System.out.println("D:/temp/car-park-test.csv");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
	 
			Date previousDate = null;
			Date parsedDate = null;
			long diff = 0;
			
			System.out.print("Enter the log you wish to replay > ");
	        String csvFile = br.readLine();
			
			br = new BufferedReader(new FileReader(csvFile));
			
			// Remove first line in case it is the header
			line = br.readLine();
			
			while ((line = br.readLine()) != null) {							
	 
			    // use comma as separator
				String[] can = line.split(cvsSplitBy);
	 				
				previousDate = parsedDate;
				
				try {
					parsedDate = formatter.parse(can[0]);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				//
				if (parsedDate != null && previousDate != null)
					diff = parsedDate.getTime() - previousDate.getTime();
				
				try {
					if ( diff > 1000 ) diff = 1000;
					Thread.sleep(diff);					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
								
				System.out.println("Delay = " + diff + " : CanPackey [time = " + can[0] + " , id = " + can[2] + " , data=" + can[4] + "]");	 
				carTestUtils.sendCan("0" + can[2].substring(3, 6),can[4].substring(3, 19));				
				
			}
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	 
		System.out.println("Done");
	  }		
		
		
		
		
}
		 
      
