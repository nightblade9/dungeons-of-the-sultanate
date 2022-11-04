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

        // Act
        controller.checkAndLevelUp(playerId.toHexString(), xpRequired);

        // Assert
        // What we're trying to capture: the saved player stats
        var argument = ArgumentCaptor.forClass(PlayerStats.class);
        Mockito.verify(statsService).save(argument.capture());
        var actual = argument.getValue();
        ;
    }
}
