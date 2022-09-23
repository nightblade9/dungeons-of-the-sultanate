package com.deengames.dungeonsofthesultanate.turnservice;

import com.deengames.dungeonsofthesultanate.turnservice.stuff.ServiceThing;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class TurnServiceApplication {

	@Autowired
	private ServiceThing realThing;

	public static void main(String[] args) {
		SpringApplication.run(TurnServiceApplication.class, args);
	}

	@PostConstruct
	// Called after static void main
	private void postConstruct() throws InterruptedException {
		this.main();
	}

	private void main() throws InterruptedException {
		this.realThing.goGoGo();
	}
}
