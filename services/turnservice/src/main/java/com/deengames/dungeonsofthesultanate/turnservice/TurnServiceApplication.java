package com.deengames.dungeonsofthesultanate.turnservice;

import com.deengames.dungeonsofthesultanate.turnservice.api.MessageQueueGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class TurnServiceApplication {

	@Autowired
	private MessageQueueGateway messageQueueGateway;

	public static void main(String[] args) {
		SpringApplication.run(TurnServiceApplication.class, args);
	}

	@PostConstruct
	// Called after static void main
	private void postConstruct() throws InterruptedException {
		this.main();
	}

	private void main() throws InterruptedException {
		// Loops "forever" and processes messages
		this.messageQueueGateway.waitForMessages();
	}
}
