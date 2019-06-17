package com.prohelion.spring;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import com.google.common.base.Predicates;

@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    
    @Bean
    public Docket api() { 
    	Set<String> PRODUCES_AND_CONSUMES_JSON = new HashSet<String>(Arrays.asList("application/json"));
    	
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()
          .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot"))) 
          .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.cloud"))) 
          .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.data.rest.webmvc"))) 
          .apis(RequestHandlerSelectors.basePackage("com.prohelion.web.controller"))              
          .paths(PathSelectors.any())          
          .build()
          .apiInfo(apiInfo())
          .produces(PRODUCES_AND_CONSUMES_JSON)
          .consumes(PRODUCES_AND_CONSUMES_JSON);                                           
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfo(
          "Prohelion Telemetry API", 
          "Provides access to the Prohelion Telemetry system via API.", 
          "API TOS", 
          "Terms of service", 
          new Contact("Cameron Tuesley", "www.prohelion.com", "cameron@prohelion.com"), 
          "License of API", "API license URL", Collections.emptyList());
    }
}