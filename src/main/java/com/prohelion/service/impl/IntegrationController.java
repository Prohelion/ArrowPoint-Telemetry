package com.prohelion.service.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
public class IntegrationController {

	@Bean
	public IntegrationFlow controlBus() {
	    return IntegrationFlows.from(controlChannel())
	            .controlBus()
	            .get();
	}

	@Bean
	public MessageChannel controlChannel() {
	    return MessageChannels.direct().get();
	}
	
}
