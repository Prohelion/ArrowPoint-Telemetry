package au.com.teamarrow.web.controller;

import java.util.ArrayList;
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
import au.com.teamarrow.rules.Rule;
import au.com.teamarrow.service.MeasurementDataService;

@Controller
@RequestMapping(value = "/")
@Transactional(readOnly = true)
public class RulesController extends AbstractController {
    
    public RulesController() {       
    }
    
    @RequestMapping(value = { "/rules.html" }, method = RequestMethod.GET)
    public String getRulesDashboard(Model model) {
        return "rules";
    }
    
    
    @RequestMapping(value = { "/rules.json" }, method = RequestMethod.GET)
    public @ResponseBody List<Rule> getRules() {
        List<Rule> ruleData = new ArrayList<Rule>();
        
        return ruleData;
    }
}
