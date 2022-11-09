package com.deengames.dungeonsofthesultanate.playerservice.stats.experience;

import com.deengames.dungeonsofthesultanate.playerservice.stats.StatsService;
import net.minidev.json.JSONObject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class LevelController {

    @Autowired
    private StatsService statsService;

    @PostMapping(value="/levelup/{userId}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public JSONObject checkAndLevelUp(@PathVariable String userId) {
        var objectId = new ObjectId(userId);
        var player = statsService.get(objectId).get();
        var experiencePoints = player.getExperiencePoints();

        var previousLevel = player.getLevel();
        var currentLevel = ExperiencePointsCalculator.whatLevelAmI(experiencePoints);
        if (currentLevel < previousLevel) {
            throw new IllegalStateException("XP calculator bug");
        }

        var result = new JSONObject();

        if (currentLevel == previousLevel) {
            result.put("levels_gained", 0);
            result.put("logs", new String[0]);
            return result;
        }

        var levelDiff = currentLevel - previousLevel;
        player.setLevel(currentLevel);
        var hpIncrease = 0;
        var spIncrease = 0;

        // The only way to ensure bulk-level-ups (e.g. jump 5 levels) give exactly the same stats points as incremental
        // ones, is to incrementally upgrade. It's floating-point math, don't look at me like that.
        for (var i = 0; i < levelDiff; i++) {
            var newHp = (int) Math.round(player.getMaxHealth() * (1 + StatsGains.HP_UP_PERCENT_PER_LEVEL));
            hpIncrease += newHp - player.getMaxHealth();
            player.setMaxHealth(newHp);

            var newSp = (int) Math.round(player.getMaxEnergy() * (1 + StatsGains.SP_UP_PERCENT_PER_LEVEL));
            spIncrease += newSp - player.getMaxEnergy();
            player.setMaxEnergy(newSp);
        }

        var attackIncrease = (levelDiff * StatsGains.ATTACK_UP_PER_LEVEL);
        var defenseIncrease = (levelDiff * StatsGains.DEFENSE_UP_PER_LEVEL);
        var specialAttackIncrease = (levelDiff * StatsGains.SPECIAL_ATTACK_UP_PER_LEVEL);
        var specialDefenseIncrease = (levelDiff * StatsGains.SPECIAL_DEFENSE_UP_PER_LEVEL);
        var speedIncrease = (levelDiff * StatsGains.SPEED_UP_PER_LEVEL);

        player.setAttack(player.getAttack() + attackIncrease);
        player.setDefense(player.getDefense() + defenseIncrease);
        player.setSpecialAttack(player.getSpecialAttack() + specialAttackIncrease);
        player.setSpecialDefense(player.getSpecialDefense() + specialDefenseIncrease);
        player.setSpeed(player.getSpeed() + speedIncrease);

        statsService.save(player);

        var levelWord = "level";
        if (levelDiff > 1) {
            levelWord = "levels";
        }

        result.put("levels_gained", levelDiff);
        result.put("logs", new String[] {
            String.format("You gained %s %s!", levelDiff, levelWord),
            String.format("Gained %s health!", hpIncrease),
            String.format("Gained %s energy!", spIncrease),
            String.format("Gained %s attack and %s special attack!", attackIncrease, specialAttackIncrease),
            String.format("Gained %s defense and %s special defense!", defenseIncrease, specialDefenseIncrease),
            String.format("Gained %s speed!", speedIncrease)
        });
        return result;
    }
}
