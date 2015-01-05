import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import au.com.teamarrow.utils.test.CruiseControlStepTest;
import au.com.teamarrow.utils.test.CruiseControlTest;
import au.com.teamarrow.utils.test.CruiseSimulator;
import au.com.teamarrow.utils.test.LogReplayer;


public class CarUtilities {

	public static void main(String[] args) {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int selection = 0;
		
		System.out.println("TeamArrow Car Testing Utilities");
		System.out.println("");
		System.out.println("What utility would you like to run?");
		System.out.println("-----------------------------------");
		System.out.println("0) Exit");
		System.out.println("1) Cruise Control Test");
		System.out.println("2) Cruise Control Step Test");
		System.out.println("3) Cruise Simulator");
		System.out.println("4) CanBus Log Replayer");
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
	        case 1: CruiseControlTest.main(args); break;
	        case 2: CruiseControlStepTest.main(args); break;
	        case 3: CruiseSimulator.main(args); break;
	        case 4: LogReplayer.main(args); break;
        	              
        }
        
	}

}
