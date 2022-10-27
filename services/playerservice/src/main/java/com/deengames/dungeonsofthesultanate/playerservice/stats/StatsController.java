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
        var stats = statsService.get(new ObjectId(userId));
        if (stats.isEmpty()) {
            return null;
        }
        return stats.get();
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

        // Validate "absolutely" (e.g. invalid player level)
        this.validateStats(updatedStats);

        var objectId = new ObjectId(id);
        updatedStats.setPlayerId(objectId);

        var previousStats = statsService.get(objectId);
        if (previousStats.isEmpty()) {
            throw new IllegalArgumentException(String.format("No stats found for player %s", id));
        }

        // TODO: validate logic. Ya3ne, did the level go down? Did XP drop, or max health? That's a bug.
        statsService.save(updatedStats);
    }

    private static void validateStats(PlayerStats stats) {
        if (stats.getLevel() <= 0) {
            throw new IllegalArgumentException("Level must be positive");
        }
        if (stats.getExperiencePoints() < 0) {
            throw new IllegalArgumentException("XP must be non-negative");
        }

        // TODO: check health and energy don't exceed max and are non-negative
        // TODO: check stats
    }
}
