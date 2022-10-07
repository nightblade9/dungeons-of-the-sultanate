package com.deengames.dungeonsofthesultanate.encounterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EncounterServiceApplication {

	public static void main(String[] args) {
		System.setProperty(org.springframework.core.env.AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "dev");
		SpringApplication.run(EncounterServiceApplication.class, args);
	}

}
