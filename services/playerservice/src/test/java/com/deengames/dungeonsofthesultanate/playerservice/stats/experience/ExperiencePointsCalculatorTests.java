package com.deengames.dungeonsofthesultanate.playerservice.stats.experience;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ExperiencePointsCalculatorTests {

    @ParameterizedTest
    @ValueSource(ints = {-193, -1, 0, 1})
    void experiencePointsRequiredForLevel_Throws_IfLevelIsInvalid(int level) {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> ExperiencePointsCalculator.experiencePointsRequiredForLevel(level));
    }

    @Test
    void experiencePointsRequiredForLevel_IncreasesForHigherLevels() {
        // Act
        var levelOne = ExperiencePointsCalculator.experiencePointsRequiredForLevel(2);
        var levelTwo = ExperiencePointsCalculator.experiencePointsRequiredForLevel(5);
        var levelTen = ExperiencePointsCalculator.experiencePointsRequiredForLevel(10);

        // Assert
        Assertions.assertTrue(levelTwo > levelOne);
        Assertions.assertTrue(levelTen > levelTwo);
    }
}
