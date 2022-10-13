package com.deengames.dungeonsofthesultanate.playerservice.stats;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping(value="/stats/{userId}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public PlayerStats getStats(@PathVariable String userId) {
        return statsService.get(new ObjectId(userId));
    }

    @PostMapping(value="/stats", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createStats(@RequestBody String userId) {
        var stats = new PlayerStats(); // sets defaults
        var objectId = new ObjectId(userId);
        stats.setPlayerId(objectId);
        statsService.save(stats);
    }

    @PutMapping(value="/stats/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public void updateStats(@PathVariable String id, @RequestBody PlayerStats updatedStats) {
        // Externalities only have the ID, not the ObjectId.

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("playerID");
        }

        var objectId = new ObjectId(id);
        updatedStats.setPlayerId(objectId);

        var isExisting = statsService.exists(objectId);
        if (!isExisting) {
            throw new IllegalArgumentException(String.format("No stats found for player %s", id));
        }

        statsService.save(updatedStats);
    }
}
