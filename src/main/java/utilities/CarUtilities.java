package utilities;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(scanBasePackages={"utilities","au.com.teamarrow.canbus.serial"})
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@ImportResource({"classpath:/META-INF/spring/utilities/spring-utilities.xml"})
public class CarUtilities implements CommandLineRunner {
	
	@Autowired
	CruiseControlTest cruiseControlTest;
	
	@Autowired
	CruiseControlStepTest cruiseControlStepTest;
	
	//@Autowired
	//CruiseSimulator cruiseSimulator;

	@Autowired
	LogReplayer logReplayer;
	
    public static void main(String[] args) throws Exception {    
    	SpringApplication app = new SpringApplication(CarUtilities.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);    	
    }

    //access command line arguments
    @Override
    public void run(String... args) throws Exception {
	
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int selection = 0;
		
		System.out.println("TeamArrow Car Testing Utilities");
		System.out.println("");
		System.out.println("What utility would you like to run?");
		System.out.println("-----------------------------------");
		System.out.println("0) Exit");
		System.out.println("1) Cruise Control Test");
		System.out.println("2) Cruise Control Step Test");
		System.out.println("3) CanBus Log Replayer");
		System.out.println("");
		System.out.print("Make your selection > ");
		
        try {
			selection = Integer.parseInt(br.readLine());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("");
		System.out.println("===================================");
		System.out.println("");
                
        switch ( selection ) {
        
	        case 0: break;
	        case 1: cruiseControlTest.start(args); break;
	        case 2: cruiseControlStepTest.start(args); break;
	        case 3: logReplayer.start(args); break;
        	              
        }
        
		
    }
}
	
