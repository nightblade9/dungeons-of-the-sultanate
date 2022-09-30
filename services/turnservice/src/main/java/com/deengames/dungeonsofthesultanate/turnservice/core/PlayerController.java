package com.deengames.dungeonsofthesultanate.turnservice.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

    @Autowired
    private TurnService service;

    // TODO: delete
    @GetMapping(value="/player", consumes=MediaType.TEXT_PLAIN_VALUE)
    public int getPlayer(@RequestBody String userId) {
        return service.getNumTurns(userId);
    }

    @PostMapping(value = "/player", consumes = MediaType.TEXT_PLAIN_VALUE)
    public void createPlayer(@RequestBody String userId)
    {
        var data = new PlayerTurns();
        data.setUserId(userId);
        service.addNewRecord(data);
    }
}
