package au.com.teamarrow.utils.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import au.com.teamarrow.service.CruiseSimulatorService;

@ContextConfiguration(locations = {"/spring-simulator-test.xml"})
public class CruiseSimulator {

    @Autowired
   	private CarTestUtils carTestUtils;
        
    @Autowired
   	private CruiseSimulatorService cruiseSimulatorService;
       
    public static void main (String[] arg) {

    	ClassPathXmlApplicationContext context =  new ClassPathXmlApplicationContext("/spring-simulator-test.xml");
    	CruiseSimulator cruiseSimulator = context.getBean(CruiseSimulator.class);
    	cruiseSimulator.start(arg);    	    	
    	context.registerShutdownHook();
    	    	
    }
	
    	
    private void start (String[] arg) {
    	
    	System.out.println("Cruise Control Simulator is now Running");
    	System.out.println("Remove the file ./run.txt to stop execution");
    	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
		        System.out.print("Enter your noise variation percent 100% (0.1 is default) > ");
		        double variationPercent = (double) (Double.parseDouble(br.readLine()) / (double)100);		        
		        
		        System.out.print("Enter your noise hertz (5 is default, 0 to disable) > ");
		        int variationHertz = Integer.parseInt(br.readLine());
		        
		        cruiseSimulatorService.setVariationHertz(variationHertz);		        
		        cruiseSimulatorService.setVelocityVariationPercent(variationPercent);		        
		        
		} catch (Exception ex) {
				System.out.print("SOMETHING WENT WRONG - EXITING");
				return;
		}
    	
    	
		System.out.println();
    	System.out.println("Setpoint %,Current Velocity,Current RPM,Cruise Target Rpm,Proportional Factor,Integral Factor,Combined Error,Cruise Status");    	
    	   	    	   
    	File f = new File("run.txt");
    	
    	while (f.exists() && !f.isDirectory()) {
	  	 		  	 	
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  	       
			f = new File("run.txt");

  	    }    	
   	    	

    	
    	System.out.println("./run.txt has been removed");
    	System.out.println("Cruise Control Simulator is shutting down");
    	
    	
    }
	
}
