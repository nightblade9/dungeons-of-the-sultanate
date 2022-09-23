package com.deengames.dungeonsofthesultanate.turnservice.stuff;


import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InnerClassThing
{
    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private AmqpTemplate amqpTemplate;

    private final String queueName = "testQueue";

    private boolean gotMessage = false;

    public void go() throws InterruptedException {
        while (!gotMessage)
        {
            Thread.sleep(100);
        }
    }

    @RabbitListener(queues = "testQueue")
    public void listen(String in) {
        gotMessage = true;
        System.out.println("Message read from myQueue : " + in);
    }
}