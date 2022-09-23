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

	private static ServiceThing service;

	@Autowired
	private ServiceThing realThing;

	@PostConstruct
	private void postConstruct()
	{
		service = realThing;
	}

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(TurnServiceApplication.class, args);
		service.goGoGo();
	}

}
