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

    @PostMapping(value="/levelup/{userId}/{experiencePoints}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public JSONObject checkAndLevelUp(@PathVariable String userId, @PathVariable int experiencePoints) {
        var objectId = new ObjectId(userId);
        var player = statsService.get(objectId).get();

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

        // TODO: test that leveling up from 1-5 incrementally gives the same HP as jumping from 1-5
        var unrolledHpIncreasePercent = Math.pow(1 + StatsGains.HP_UP_PERCENT_PER_LEVEL, levelDiff);
        var hpIncrease = (int)Math.round(player.getMaxHealth() * unrolledHpIncreasePercent);
        player.setMaxHealth(player.getMaxHealth() + hpIncrease);

        var unrolledSpIncreasePercent = Math.pow(1 + StatsGains.SP_UP_PERCENT_PER_LEVEL, levelDiff);
        var spIncrease = (int)Math.round(player.getMaxHealth() * unrolledSpIncreasePercent);
        player.setMaxEnergy(player.getMaxEnergy() + spIncrease);

        var attackIncrease = (levelDiff * StatsGains.attackUpPerLevel);
        var defenseIncrease = (levelDiff * StatsGains.defenseUpPerLevel);
        var specialAttackIncrease = (levelDiff * StatsGains.specialAttackUpPerLevel);
        var specialDefenseIncrease = (levelDiff * StatsGains.specialDefenseUpPerLevel);
        var speedIncrease = (levelDiff * StatsGains.speedUpPerLevel);

        player.setAttack(player.getAttack() + attackIncrease);
        player.setDefense(player.getDefense() + defenseIncrease);
        player.setSpecialAttack(player.getSpecialAttack() + specialAttackIncrease);
        player.setSpecialDefense(player.getSpecialDefense() + specialDefenseIncrease);
        player.setSpeed(player.getSpeed() + speedIncrease);

        statsService.save(player);

        result.put("levels_gained", 0);
        result.put("logs", new String[] {
            String.format("You gained %s levels!", levelDiff),
            String.format("Gained %s health!", hpIncrease),
            String.format("Gained %s energy!", spIncrease),
            String.format("Gained %s attack and %s special attack!", attackIncrease, specialAttackIncrease),
            String.format("Gained %s defense and %s special defense!", defenseIncrease, specialDefenseIncrease),
            String.format("Gained %s speed!", speedIncrease)
        });
        return result;
    }
}
