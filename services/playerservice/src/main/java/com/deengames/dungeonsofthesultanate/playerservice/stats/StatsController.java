package com.deengames.dungeonsofthesultanate.playerservice.stats;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatsController {

    @Autowired
    private StatsService statsService;

    @PostMapping(value="/stats", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createStats(@RequestBody String userId) {
        var stats = new PlayerStats(); // sets defaults
        var objectId = new ObjectId(userId);
        stats.setPlayerId(objectId);
        statsService.savePlayerStats(stats);
    }
}
