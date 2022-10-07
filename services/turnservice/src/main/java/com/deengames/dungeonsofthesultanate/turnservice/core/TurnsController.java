package com.deengames.dungeonsofthesultanate.turnservice.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class TurnsController {

    @Autowired
    private TurnService service;

    @GetMapping(value="/turns", consumes=MediaType.APPLICATION_JSON_VALUE)
    public int getTurns(String userId) {
        var data = service.getTurns(userId);
        if (data == null)
        {
            return 0;
        }

        return data.getNumTurns();
    }

    @PostMapping(value = "/turns", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createTurns(@RequestBody String userId) {
        var data = new PlayerTurns();
        data.setUserId(userId);
        service.save(data);
    }

    @PatchMapping(value="/turns", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean consumeTurn(@RequestBody String userId) {

        var turns = service.getTurns(userId);
        if (turns == null)
        {
            throw new IllegalStateException(String.format("Player %s doesn't have turn data", userId));
        }

        if (turns.getNumTurns() <= 0)
        {
            return false; // Multiple tabs open and had an action but no turns? Meh.
        }

        turns.setNumTurns(turns.getNumTurns() - 1);
        service.save(turns);
        return true;
    }
}
