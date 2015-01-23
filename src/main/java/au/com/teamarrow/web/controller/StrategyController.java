package au.com.teamarrow.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class StrategyController extends AbstractController {
    		
    public StrategyController() {
        
    }    
        
    @RequestMapping(value = { "/strategy.html" }, method = RequestMethod.GET)
    public String getLapTracker(Model model) {
        return "strategy";
    }       
   
}
