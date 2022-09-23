package com.deengames.dungeonsofthesultanate.services.web.maps.toweringtreeforest;

import com.deengames.dungeonsofthesultanate.services.web.maps.world.WorldMapLocations;
import com.deengames.dungeonsofthesultanate.services.web.messagequeue.MessageQueueWriter;
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
public class ToweringTreeForestController {

    @Autowired
    private MessageQueueWriter messageQueueWriter;

    @GetMapping("/map/towering-tree-forest")
    public String worldMap(Model model) {
        model.addAttribute("location", WorldMapLocations.locations[0]);
        messageQueueWriter.putMessage();
        return "map/toweringtreeforest/index";
    }
}
