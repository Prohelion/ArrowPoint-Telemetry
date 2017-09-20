package au.com.teamarrow.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import au.com.teamarrow.model.Device;
import au.com.teamarrow.service.DeviceService;

@Controller
public abstract class AbstractController {

    @Autowired
    protected DeviceService deviceService;
    
    // @ModelAttribute - don't do this - the annotation makes it get called every JSON request which is bad
    public List<Device> getDevices() {
        return deviceService.getDevices();
    }
    
}
