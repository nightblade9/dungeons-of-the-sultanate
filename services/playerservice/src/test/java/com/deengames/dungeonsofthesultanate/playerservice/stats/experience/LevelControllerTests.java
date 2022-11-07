package com.deengames.dungeonsofthesultanate.playerservice.stats.experience;

import com.deengames.dungeonsofthesultanate.playerservice.stats.PlayerStats;
import com.deengames.dungeonsofthesultanate.playerservice.stats.StatsService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
class LevelControllerTests {

    @Autowired
    private LevelController controller;

    @MockBean
    private StatsService statsService;

    @ParameterizedTest
    @ValueSource(ints={1, 5})
    void checkAndLevelUp_levelsUpPlayer_andIncreasesStatsByLevelsIncreased(int levelsGained) {
        // Arrange
        var stats = new PlayerStats();
        var playerId = new ObjectId();

        Mockito.when(statsService.get(playerId)).thenReturn(Optional.of(stats));
        var xpRequired = ExperiencePointsCalculator.experiencePointsRequiredForLevel(levelsGained + 1);

        stats.setExperiencePoints(xpRequired);

        // Act
        controller.checkAndLevelUp(playerId.toHexString());

        // Assert
        // What we're trying to capture: the saved player stats
        var argument = ArgumentCaptor.forClass(PlayerStats.class);
        Mockito.verify(statsService).save(argument.capture());
        var actual = argument.getValue();

        Assertions.assertEquals(1 + levelsGained, actual.getLevel());
        // Meh, just make sure we have *more* of everything...
        var baseline = new PlayerStats();
        Assertions.assertTrue(actual.getMaxHealth() > baseline.getMaxHealth());
        Assertions.assertTrue(actual.getMaxEnergy() > baseline.getMaxEnergy());
        Assertions.assertTrue(actual.getAttack() > baseline.getAttack());
        Assertions.assertTrue(actual.getSpecialAttack() > baseline.getSpecialAttack());
        Assertions.assertTrue(actual.getDefense() > baseline.getDefense());
        Assertions.assertTrue(actual.getSpecialDefense() > baseline.getSpecialDefense());
        Assertions.assertTrue(actual.getSpeed() > baseline.getSpeed());
    }

    @Test
    void checkAndLevelUp_GivesEqualHealthAndSkillPoints_IfYouLevelUpIndividuallyVsAllAtOnce() {
        var statsIncremental = new PlayerStats();
        var statsAllAtOnce = new PlayerStats();
        var playerIdIncremental = new ObjectId();
        var playerIdAllAtOnce = new ObjectId();

        Mockito.when(statsService.get(playerIdIncremental)).thenReturn(Optional.of(statsIncremental));
        Mockito.when(statsService.get(playerIdAllAtOnce)).thenReturn(Optional.of(statsAllAtOnce));

        // Act
        // Incremental first
        for (var i = 2; i <= 5; i++) {
            var xpRequired = ExperiencePointsCalculator.experiencePointsRequiredForLevel(i);
            statsIncremental.setExperiencePoints(xpRequired);
            controller.checkAndLevelUp(playerIdIncremental.toHexString());
        }

        // All-at-once next
        var xpRequired = ExperiencePointsCalculator.experiencePointsRequiredForLevel(5);
        statsAllAtOnce.setExperiencePoints(xpRequired);
        controller.checkAndLevelUp(playerIdAllAtOnce.toHexString());

        // Assert
        Assertions.assertEquals(statsIncremental.getMaxHealth(), statsAllAtOnce.getMaxHealth());
        Assertions.assertEquals(statsIncremental.getMaxEnergy(), statsAllAtOnce.getMaxEnergy());
    }
}
