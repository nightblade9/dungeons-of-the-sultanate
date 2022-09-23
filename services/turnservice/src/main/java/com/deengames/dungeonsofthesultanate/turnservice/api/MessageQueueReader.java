package com.deengames.dungeonsofthesultanate.turnservice.api;


import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageQueueReader
{
    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private AmqpTemplate amqpTemplate;

    private final String queueName = "dotsQueue";

    private boolean isRunning = false;


    public void waitForMessages() throws InterruptedException {
        this.isRunning = true;
        while (this.isRunning)
        {
            var message = this.amqpTemplate.receive(queueName);
            if (message != null)
            {
                System.out.println("Got: " + message);
            }
            Thread.sleep(100);
        }
    }

    /**
     * RabbitMQ magic: deleting this message makes messages stop being received. Adding it back in, does nothing
     * (it's never invoked), but messages receive again. Deleting it AGAIN makes messages STILL get received. Wut...?
     */
//    @RabbitListener(queues = queueName)
//    public void receiveMessage(String message) {
//        System.out.println(message);
//    }
}