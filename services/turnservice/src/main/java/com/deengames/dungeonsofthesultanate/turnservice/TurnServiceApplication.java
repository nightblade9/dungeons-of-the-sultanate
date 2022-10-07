package com.deengames.dungeonsofthesultanate.turnservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TurnServiceApplication {

	public static void main(String[] args) {
		// Set the default profile to `dev`
		System.setProperty(org.springframework.core.env.AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "dev");
		SpringApplication.run(TurnServiceApplication.class, args);
	}

}
