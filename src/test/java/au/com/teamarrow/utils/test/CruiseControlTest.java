package au.com.teamarrow.utils.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration(locations = {"/spring-test.xml"})
public class CruiseControlTest {

	public static final double KPH_TO_RPM = 9.81;
		
	byte[] bytes = new byte[50];
	
    @Autowired
   	private CarTestUtils carTestUtils;

    public static void main (String[] arg) {
    	
    	ConfigurableApplicationContext context =  new ClassPathXmlApplicationContext("spring-test.xml");
    	CruiseControlTest cruiseTest = context.getBean(CruiseControlTest.class);
    	cruiseTest.start(arg);
    	context.close();
    	    	
    }

	
	private void start (String[] arg) {
		
		int i = 0;
	
		Integer targetRpm = 0;
		double proportional;
		double integral;
		double derivative;
		int duration;
		
		System.out.println("Cruise Control Mission Control V1.0");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		//	Main loop, run until ctrl+c ...
		do {
			try {
		        System.out.print("Enter target speed (kph) > ");
		        targetRpm = (int) (Integer.parseInt(br.readLine()) * KPH_TO_RPM);
		        
		        System.out.print("Enter P (1000) > ");
		        proportional = Double.parseDouble(br.readLine());
		        
		        System.out.print("Enter I (100) > ");
		        integral = Double.parseDouble(br.readLine());
		        
		        System.out.print("Enter D (800) > ");
		        derivative = Double.parseDouble(br.readLine());
		        
		        System.out.print("Run for (seconds) > ");
		        duration = Integer.parseInt(br.readLine());

			} catch (Exception ex) {
				System.out.print("SOMETHING WENT WRONG - EXITING - CRUISE CONTROL MAY STILL BE SET");
				return;
			}
		        
	        System.out.printf("Running ccmc with target [RPM %d, P %f, I %f, D %f]", 
	        		targetRpm, proportional, integral, derivative);
	 
	        System.out.println(); System.out.println();

	        carTestUtils.sendPID(new Double(proportional).intValue(),new Double(integral).intValue(),new Double(derivative).intValue());
	        carTestUtils.sendRPM(targetRpm);
	        
	        i = 0;
	        do {
	        	
	        	try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	i++;
	        } while (i < duration);

	    	
	    	//	Reset input vars
	        targetRpm = 0;
	    	proportional = 0;
	    	integral = 0;
	    	derivative = 0;
	    	duration = 0;
		        	        	        
		} while (true);
	}
	
}
