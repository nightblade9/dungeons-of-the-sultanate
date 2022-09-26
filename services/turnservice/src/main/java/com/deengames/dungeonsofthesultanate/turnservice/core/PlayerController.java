package com.deengames.dungeonsofthesultanate.turnservice.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class PlayerController {

    @Autowired
    private TurnService service;

    @PostMapping(value = "/player", consumes = "application/json", produces = "application/json")
    public void createPlayer(@RequestBody String playerId)
    {
        var data = new PlayerTurns();
        data.setPlayerId(playerId);
        service.addNewRecord(data);
    }
}
