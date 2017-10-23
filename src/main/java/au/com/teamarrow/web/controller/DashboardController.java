package au.com.teamarrow.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import au.com.teamarrow.model.MeasurementData;
import au.com.teamarrow.service.MeasurementDataService;

@Controller
@RequestMapping(value = "/")
public class DashboardController extends AbstractController {

    private static final Integer WS22_VELOCITY = 0x403;
    
    private static final Integer WS22_HEATSINK_MOTOR_TEMPS = 0x40B;
    
    private static final Integer WS22_DSP_TEMP = 0x40C;
    
    private static final Integer EVDC_MOTORDRIVE = 0x501;
    
    private static final Integer BMS_STATE_OF_CHARGE = 0x6F4;
    
    private static final Integer BMS_PACK_VOLTAGE_CURRENT = 0x6FA;
    
    private static final Integer MPPT1_DATA = 0x701;
    
    private static final Integer MPPT2_DATA = 0x705;
    
    private static final Integer MPPT3_DATA = 0x709;
    
    private double MPPT1Power = (double) 0;
    
    private double MPPT2Power = (double) 0;
    
    private double MPPT3Power = (double) 0;
          
    @Autowired
    private MeasurementDataService measurementDataService;
    
    public DashboardController() {
        
    }
    
    @Transactional
    @RequestMapping(value = { "/", "/index.html" }, method = RequestMethod.GET)
    public String getDashboard(Model model) {
        model.addAttribute("devices", getDevices());
        
        return "index";
    }

    
    @Transactional
    @RequestMapping(value = { "/index.json" }, method = RequestMethod.GET, params = { "canId" })
    @ResponseBody
    public List<MeasurementData> getBMSCanData(@RequestParam(required = true) Integer canId)
    {
    	return measurementDataService.findLatestDataForCanId(canId);
    }
    
    @Transactional(readOnly = true)
    @RequestMapping(value = { "/battery-pack.json" }, method = RequestMethod.GET)
    @ResponseBody
    public BatteryPackDto getBatteryPackData() {
        List<MeasurementData> soc = measurementDataService.findLatestDataForCanId(BMS_STATE_OF_CHARGE);
        List<MeasurementData> pvc = measurementDataService.findLatestDataForCanId(BMS_PACK_VOLTAGE_CURRENT);
        
        if (soc.size() == 0 || pvc.size() == 0) {
            return null;
        }
        
        return new BatteryPackDto(soc, pvc); 
    }
    
    @Transactional(readOnly = true)
    @RequestMapping(value = { "/motor-controller-temperatures.json" }, method = RequestMethod.GET)
    @ResponseBody
    public MotorControllerTemperatureDto getMotorControllerTemperatureData() {
        List<MeasurementData> hmt = measurementDataService.findLatestDataForCanId(WS22_HEATSINK_MOTOR_TEMPS);
        List<MeasurementData> dt = measurementDataService.findLatestDataForCanId(WS22_DSP_TEMP);
        
        if (hmt.size() == 0 && dt.size() == 0) {
            return null;
        }
        
        return new MotorControllerTemperatureDto(hmt, dt); 
    }
    
    @Transactional(readOnly = true)
    @RequestMapping(value = { "/vehicle-velocity.json" }, method = RequestMethod.GET)
    @ResponseBody
    public VehicleVelocityDto getVehicleVelocityData() {
        List<MeasurementData> vt = measurementDataService.findLatestDataForCanId(WS22_VELOCITY);
        List<MeasurementData> mt = measurementDataService.findLatestDataForCanId(EVDC_MOTORDRIVE);
        
        if (vt.size() == 0 && mt.size() == 0) {
            return null;
        }
        
        return new VehicleVelocityDto(vt, mt); 
    }
    
    @Transactional(readOnly = true)
    @RequestMapping(value = { "/power-trackers.json" }, method = RequestMethod.GET)
    @ResponseBody
    public PowerTrackerDto getPowerTrackerData(@RequestParam(required = true) Integer ptIdx) {
        Integer canId = null;
        switch(ptIdx) {
            case 1: canId = MPPT1_DATA;
                break;
            case 2: canId = MPPT2_DATA;
                break;
            case 3: canId = MPPT3_DATA;
                break;
        }
        
        List<MeasurementData> pt = measurementDataService.findLatestDataForCanId(canId);
        
        if (pt.size() == 0) {
            return null;
        }
        
        PowerTrackerDto powerTrackerDto = new PowerTrackerDto(pt);
        
        switch(ptIdx) {
	        case 1: MPPT1Power = powerTrackerDto.getPower().getFloatValue();
	            break;
	        case 2: MPPT2Power = powerTrackerDto.getPower().getFloatValue();
	            break;
	        case 3: MPPT3Power = powerTrackerDto.getPower().getFloatValue();
	            break;
        }
        
        powerTrackerDto.setTotalPower(MPPT1Power + MPPT2Power + MPPT3Power);
        
        return powerTrackerDto; 
    }
    
    public class BatteryPackDto {
        MeasurementData socAh;
        MeasurementData socPercentage;
        MeasurementData packVoltage;
        MeasurementData packCurrent;
        Double packPower;
        
        public BatteryPackDto() {
            
        }
        
        public BatteryPackDto(List<MeasurementData> soc, List<MeasurementData> pvc) {
            MeasurementData a = soc.get(0);
            if (a.getDataPointCanId() == 0x6F40) {
                this.socAh = a;
                this.socPercentage = soc.get(1);
            } else {
                this.socAh = soc.get(1);
                this.socPercentage = a;
            }
            
            a = pvc.get(0);
            if (a.getDataPointCanId() == 0x6FA0) {
                this.packCurrent = pvc.get(1);
                this.packVoltage = a;
            } else {
                this.packCurrent = a;
                this.packVoltage = pvc.get(1);
            }
            
            
        }

        public MeasurementData getSocAh() {
            return socAh;
        }

        public void setSocAh(MeasurementData socAh) {
            this.socAh = socAh;
        }
        
        public MeasurementData getSocPercentage() {
        	
        	MeasurementData md = new MeasurementData();
        	
        	md.setFloatValue(this.socPercentage.getFloatValue() * 100.0);
        	
            return md;
        }
        
        public void setSocPercentage(MeasurementData socPercentage) {
            this.socPercentage = socPercentage;
        }
        
        public MeasurementData getPackVoltage() {
            return packVoltage;
        }
        
        public void setPackVoltage(MeasurementData packVoltage) {
            this.packVoltage = packVoltage;
        }
        
        public MeasurementData getPackCurrent() {
            return packCurrent;
        }
        
        public void setPackCurrent(MeasurementData packCurrent) {
            this.packCurrent = packCurrent;
        }

        public Double getPackPower() {        	
            return (packVoltage.getFloatValue() * packCurrent.getFloatValue()) / 1000000;
        }
        
        public String getPackPowerFormatted() {
        	return(String.format("%1$,.2f", this.getPackPower()));
        }
       
        public void setPackPower(Double packPower) {
            this.packPower = packPower;
        }
    }
    
    public class MotorControllerTemperatureDto {
        MeasurementData ws22HeatsinkTemperature;
        MeasurementData motorTemperature;
        MeasurementData dspOperatingTemperature;
        
        public MotorControllerTemperatureDto() {
            
        }
        
        public MotorControllerTemperatureDto(List<MeasurementData> hmt, List<MeasurementData> dt) {
            this.ws22HeatsinkTemperature = (hmt.size() > 0 ? hmt.get(0) : null);
            this.motorTemperature = (hmt.size() > 1 ? hmt.get(1) : null);
            this.dspOperatingTemperature = (dt.size() > 0 ? dt.get(0) : null);
        }
      
        public MeasurementData getWs22HeatsinkTemperature() {
            return ws22HeatsinkTemperature;
        }
       
        public void setWs22HeatsinkTemperature(MeasurementData ws22HeatsinkTemperature) {
            this.ws22HeatsinkTemperature = ws22HeatsinkTemperature;
        }
      
        public MeasurementData getMotorTemperature() {
            return motorTemperature;
        }
       
        public void setMotorTemperature(MeasurementData motorTemperature) {
            this.motorTemperature = motorTemperature;
        }
      
        public MeasurementData getDspOperatingTemperature() {
            return dspOperatingTemperature;
        }
       
        public void setDspOperatingTemperature(MeasurementData dspOperatingTemperature) {
            this.dspOperatingTemperature = dspOperatingTemperature;
        }
    }
    
    public class VehicleVelocityDto {
        MeasurementData vehicleVelocity;
        Double vehicleVelocityKMH;
        MeasurementData motorRpm;
        MeasurementData throttleSetPoint;
        
        public VehicleVelocityDto() {
            
        }
        
        public VehicleVelocityDto(List<MeasurementData> vt, List<MeasurementData> mt) {
            this.vehicleVelocity = (vt.size() > 0 ? vt.get(0) : null); 
            this.vehicleVelocityKMH = vehicleVelocity.getFloatValue() * 3.6; // 60 * 60 / 1000
            this.motorRpm = (vt.size() > 1 ? vt.get(1) : null);  
            this.throttleSetPoint = (mt.size() > 0 ? mt.get(0) : null);
        }
      
        public MeasurementData getVehicleVelocity() {
            return vehicleVelocity;
        }
        
        public String getVehicleVelocityFormatted() {
        	if (vehicleVelocity.getFloatValue() == null) return ("0");
        	return String.format("%.1f", vehicleVelocity.getFloatValue());
        }
      
        public void setVehicleVelocity(MeasurementData vehicleVelocity) {
            this.vehicleVelocity = vehicleVelocity;
        }
       
        public Double getVehicleVelocityKMH() {
            return vehicleVelocityKMH;
        }

        public String getVehicleVelocityKMHFormatted() {
        	if (vehicleVelocityKMH == null) return ("0");
        	return String.format("%.1f", vehicleVelocityKMH);            
        }

        
        public void setVehicleVelocityKMH(Double vehicleVelocityKMH) {
            this.vehicleVelocityKMH = vehicleVelocityKMH;
        }
       
        public MeasurementData getMotorRpm() {
            return motorRpm;
        }
     
        public void setMotorRpm(MeasurementData motorRpm) {
            this.motorRpm = motorRpm;
        }
      
        public MeasurementData getThrottleSetPoint() {
        	
        	MeasurementData md = new MeasurementData();
        	
        	if (this.throttleSetPoint == null || this.throttleSetPoint.getFloatValue() == 0)
        		md.setFloatValue((double)0);
        	else        	
        		md.setFloatValue(this.throttleSetPoint.getFloatValue() * 100);
        	
            return md;
        }
      
        public String getThrottleSetPointFormatted() {
        	if (throttleSetPoint.getFloatValue() == null) return("0");
        	return String.format("%.0f", throttleSetPoint.getFloatValue() * 100);        	
        }
        
        public void setThrottleSetPoint(MeasurementData throttleSetPoint) {
            this.throttleSetPoint = throttleSetPoint;
        }      
    }
    
    public class PowerTrackerDto {

		MeasurementData voltage;
        MeasurementData amps;
        double totalPower;
      
        public PowerTrackerDto(List<MeasurementData> pt) {
            amps = new MeasurementData();
            voltage = new MeasurementData();
            
            amps.setFloatValue((double)pt.get(0).getFloatValue() / 1000);            
            voltage.setFloatValue((double)pt.get(1).getFloatValue() / 1000);
        }

        public MeasurementData getVoltage() {
			return voltage;
		}

		public void setVoltage(MeasurementData voltage) {
			this.voltage = voltage;
		}

		public MeasurementData getAmps() {
			return amps;
		}

		public void setAmps(MeasurementData amps) {
			this.amps = amps;
		}

		public MeasurementData getPower() {
			
			MeasurementData md = new MeasurementData();
			
			md.setFloatValue((double) ((voltage.getFloatValue() * amps.getFloatValue())));
			
			return md;
		}
		
		public void setTotalPower(double totalPower) {
			this.totalPower = totalPower;
		}
		
		public MeasurementData getTotalPower() {
			
			MeasurementData md = new MeasurementData();
			
			md.setFloatValue(totalPower);
			
			return md;
		}
		
    }
}
