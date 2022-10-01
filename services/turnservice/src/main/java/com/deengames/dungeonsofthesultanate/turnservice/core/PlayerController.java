package com.deengames.dungeonsofthesultanate.turnservice.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlayerController {

    @Autowired
    private TurnService service;

    @GetMapping(value="/player", consumes=MediaType.APPLICATION_JSON_VALUE)
    public int getPlayer(String userId) {
        return service.getNumTurns(userId);
    }

    @PostMapping(value = "/player", consumes = MediaType.TEXT_PLAIN_VALUE)
    public void createPlayer(@RequestBody String userId) {
        var data = new PlayerTurns();
        data.setUserId(userId);
        service.addNewRecord(data);
    }

    @PatchMapping(value="/player", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void consumeTurn(@RequestBody String userId) {

    }
}
