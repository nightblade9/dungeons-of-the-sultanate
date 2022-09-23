package com.deengames.dungeonsofthesultanate.services.web.messagequeue;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MessageQueueWriter {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private AmqpTemplate amqpTemplate;

    private final String queueName = "dotsQueue";

    public void putMessage()
    {
        String text = String.format("hello at %s!", new Date());
        amqpTemplate.convertAndSend(queueName, text);
    }
}
