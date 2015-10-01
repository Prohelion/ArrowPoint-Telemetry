package au.com.teamarrow.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import au.com.teamarrow.maps.Route;
import au.com.teamarrow.maps.impl.MapGenerator;


@Controller
@RequestMapping(value = "/")
public class MapController extends AbstractController {
        
	
	@Autowired
	private MapGenerator mapGenerator;
	
	@Autowired
	private Route route;
	
    public MapController() {
        
    }
        
    @RequestMapping(value = { "/maps.html" }, method = RequestMethod.GET)
    public String getMaps(Model model) {
        return "maps";
    }
    
	@RequestMapping(value="/maps/{type}", method = RequestMethod.GET, produces = "application/vnd.google-earth.kml+xml; charset=utf-8")
	public @ResponseBody String getMapInKml(@PathVariable String type) {			
		
		// Bit of a hack but this causes the route file to be reloaded
		route.setRouteFile(route.getRouteFile());
		mapGenerator.setRoute(route);
		
		return mapGenerator.generateAllLayers();

	}
    
    
        
    
   
}
