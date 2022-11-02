package com.deengames.dungeonsofthesultanate.playerservice.stats.experience;

import com.deengames.dungeonsofthesultanate.playerservice.stats.PlayerStats;
import com.deengames.dungeonsofthesultanate.playerservice.stats.StatsService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class LevelController {

    @Autowired
    private StatsService statsService;

    @GetMapping(value="/levelup/{userId}/{experiencePoints}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public String[] checkAndLevelUp(@PathVariable String userId, @PathVariable int experiencePoints) {
        var objectId = new ObjectId(userId);
        var player = statsService.get(objectId).get();

        var previousLevel = player.getLevel();
        var currentLevel = ExperiencePointsCalculator.whatLevelAmI(experiencePoints);
        if (currentLevel < previousLevel) {
            throw new IllegalStateException("XP calculator bug");
        }

        if (currentLevel == previousLevel) {
            return new String[0];
        }

        var diff = currentLevel - previousLevel;
        var statsPoints = diff * ExperiencePointsCalculator.STATS_POINTS_PER_LEVEL_UP;
        // TODO: distribute stats points
    }
}
