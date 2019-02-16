package com.prohelion.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ConfigController extends AbstractController {
       
    @RequestMapping(value = "/config.html")
    public String showConfig(Model model) {
        return "config";
    }
    
}
