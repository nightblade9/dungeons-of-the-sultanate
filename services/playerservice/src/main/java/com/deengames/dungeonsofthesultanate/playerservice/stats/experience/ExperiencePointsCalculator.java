package com.deengames.dungeonsofthesultanate.playerservice.stats.experience;

import java.util.HashMap;

// "static" class (C# meaning)
public class ExperiencePointsCalculator {

    // Since the UI shows the diff of XP in some places, and since the formula isn't personal and doesn't change,
    // why don't we just consume some memory unnecessarily and micro-optimize this, right? Right?! :facepalm: :baka:
    private static HashMap<Integer, Integer> experiencePointsCache = new HashMap<>();

    // TODO: unit tests
    public static int whatLevelAmI(int experiencePoints) {
        if (experiencePoints < 0) {
            throw new IllegalArgumentException("Please specify a non-negative XP value");
        }

        var level = 1;
        while (experiencePointsRequiredForLevel(level + 1) <= experiencePoints) {
            level++;
        }

        return level;
    }

    // TODO: hook into UI (e.g. 39/67XP)
    public static int experiencePointsRequiredForLevel(int level) {
        if (level <= 0) {
            throw new IllegalArgumentException("Level should be positive");
        }

        var key = Integer.valueOf(level);
        if (experiencePointsCache.containsKey(key)) {
            return experiencePointsCache.get(key).intValue();
        }

        // Roughly doubles the amount of XP required each level (once you reach level 10).
        // Also, I like prime numbers. Gives juicy, rough numbers - nothing nice and round.
        // 2^n and n^2 are expensive, floating-point calcs are expensive, but ya3ne, we cache.
        // =7*(2^n) + 11n^2 + 29n + 13
        // 67, 143, 255, 417, 657, 1031, 1651, 2741, 4749, ...
        var points = (7 * Math.pow(2, level)) + (11 * Math.pow(level, 2)) + (29 * level) + 13;
        // Round to int by converting to float. These values are all integers, so nothing lost.
        var xpRequired = Math.round((float)points);
        experiencePointsCache.put(key, Integer.valueOf(xpRequired));
        return xpRequired;
    }
}
