package com.deengames.dungeonsofthesultanate.encounterservice.stats;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping(value="/stats", consumes=MediaType.APPLICATION_JSON_VALUE)
    public PlayerStats getStats(String userId) {
        return statsService.get(new ObjectId(userId));
    }

    @PostMapping(value="/stats", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createStats(@RequestBody String userId) {
        var stats = new PlayerStats(); // sets defaults
        var objectId = new ObjectId(userId);
        stats.setPlayerId(objectId);
        statsService.save(stats);
    }

    @PutMapping(value="stats", consumes=MediaType.APPLICATION_JSON_VALUE)
    public void updateStats(@RequestBody PlayerStats updatedStats) {
        var playerId = updatedStats.getPlayerId();

        if (playerId == null) {
            throw new IllegalArgumentException("playerID");
        }

        var isExisting = statsService.exists(playerId);
        if (!isExisting) {
            throw new IllegalArgumentException(String.format("No stats found for player %s", playerId));
        }

        statsService.save(updatedStats);
    }
}
