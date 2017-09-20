package au.com.teamarrow.utils.test;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = {"/spring-test.xml"})
public class CruiseControlStepTest {

    @Autowired
   	private CarTestUtils carTestUtils;
        
    private void start (String[] arg) {

       	byte[] bytes = new byte[50];

       	boolean prompt = true;
 
    	System.out.println("This test could kill someone. Don't muck with it.");
    	System.out.println("Make sure the driver is prepared and that the car is stationary");
    	System.out.println("And you are well insured ;-)");
    	System.out.println("when you are ready to go, type......");
    	System.out.println("READY and hit enter to be prompted at every speed change or ");
    	System.out.println("READY-NOPROMPT to run the speed changes automatically");
    	try {
			System.in.read(bytes);
		} catch (IOException e) {
			System.out.println("AN ERROR HAS OCCURED WHILE TRYING TO GET THE COMMAND");
			return;
		}
    	
    	String response = new String(bytes);
    	
    	if (response.startsWith("READY-NOPROMPT")) prompt = false;
    	
    	if (response.startsWith("READY")) {
    	
    		carTestUtils.cruiseOff();
    		
    		carTestUtils.runAtSpeed(10,3,prompt);
    		carTestUtils.runAtSpeed(15,3,prompt);
    		carTestUtils.runAtSpeed(20,3,prompt);
    		carTestUtils.runAtSpeed(25,3,prompt);
    		carTestUtils.runAtSpeed(30,3,prompt);
    		carTestUtils.runAtSpeed(35,3,prompt);
    		carTestUtils.runAtSpeed(40,3,prompt);
    		carTestUtils.runAtSpeed(45,3,prompt);
    		carTestUtils.runAtSpeed(50,3,prompt);
    		carTestUtils.runAtSpeed(55,3,prompt);
    		carTestUtils.runAtSpeed(60,3,prompt);
    		carTestUtils.runAtSpeed(65,3,prompt);
    		carTestUtils.runAtSpeed(70,60,prompt);
    		    		    
    		carTestUtils.cruiseOff();
    		
    		return;
    		
    	}
    	
    }
    
    public static void main (String[] arg) {
    	
    	ConfigurableApplicationContext context =  new ClassPathXmlApplicationContext("spring-test.xml");
    	CruiseControlStepTest stepTest = context.getBean(CruiseControlStepTest.class);
    	stepTest.start(arg);
    	context.close();
    	    	
    }
	
}
