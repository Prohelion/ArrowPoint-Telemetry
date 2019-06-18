package com.prohelion.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class APIController extends AbstractController {
            
    @ApiIgnore
    @RequestMapping(value = { "/api/index.html" }, method = RequestMethod.GET)
    public String getAPIs(Model model) {
        return "api";
    }
    
}
