package au.com.teamarrow.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import au.com.teamarrow.model.ShortTermTrendData;

@Controller
public class DataForwardController {
	
	
	@RequestMapping(value = { "/forward-data.json" }, method = RequestMethod.PUT)
	@ResponseBody
	public String createNewArticle(@RequestBody List<ShortTermTrendData> forwardedData) {			
		return "OK";
	}

}
