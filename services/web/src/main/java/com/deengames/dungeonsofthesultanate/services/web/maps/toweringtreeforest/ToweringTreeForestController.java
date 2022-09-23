package com.deengames.dungeonsofthesultanate.services.web.maps.toweringtreeforest;

import com.deengames.dungeonsofthesultanate.services.web.maps.world.WorldMapLocations;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Controller
public class ToweringTreeForestController
{
    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    @GetMapping("/map/towering-tree-forest")
    public String worldMap(Model model) throws InstantiationException, IllegalAccessException {
        model.addAttribute("location", WorldMapLocations.locations[0]);

        var thing = MessagingProofOfConcept.class.newInstance();
        autowireCapableBeanFactory.autowireBean(thing);
        thing.putMessage();
        return "map/toweringtreeforest/index";
    }

    static class MessagingProofOfConcept
    {
        @Autowired
        private AmqpAdmin amqpAdmin;

        @Autowired
        private AmqpTemplate amqpTemplate;

        private final String queueName = "testQueue";

        @Bean
        public Queue myQueue() {
            return new Queue(queueName, false);
        }

        public void putMessage()
        {
            String text = String.format("hello at %s!", new Date());
            amqpTemplate.convertAndSend(queueName, text);
        }
    }
}
