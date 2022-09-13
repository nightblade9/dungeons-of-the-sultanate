package com.deengames.dungeonsofthesultanate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableMongoRepositories
@RestController
// TODO: remove deprecated APIs + @RestController
public class DungeonsOfTheSultanateApplication extends WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		// Set the default profile to `dev`, see README.md for why (TL;DR OAuth2 client ID/secret)
		System.setProperty(org.springframework.core.env.AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "dev");
		SpringApplication.run(DungeonsOfTheSultanateApplication.class, args);
	}
}
