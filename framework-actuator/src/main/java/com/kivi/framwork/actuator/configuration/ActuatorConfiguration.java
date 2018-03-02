package com.kivi.framwork.actuator.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kivi.framwork.actuator.mapping.ActuatorCustomizer;

@Configuration
public class ActuatorConfiguration {

	@Bean
	public ActuatorCustomizer getActuatorCustomizer() {
		ActuatorCustomizer actuator = new ActuatorCustomizer();
		return actuator;
	}
	
}
