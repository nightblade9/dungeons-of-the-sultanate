package com.deengames.dungeonsofthesultanate.playerservice.stats;

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

@SpringBootTest
public class StatsControllerTests {

    @Autowired
    private StatsController controller; // thing we're testing

    @MockBean
    private StatsService statsService;

    @Test
    public void getStats_GetsStatsFromService() {
        // Arrange
        var playerId = new ObjectId();

        var stats = new PlayerStats();
        Mockito.when(statsService.get(playerId))
            .thenReturn(stats);

        // Act
        var actual = controller.getStats(playerId.toHexString());

        // Assert
        Assertions.assertEquals(stats, actual);
    }

    @Test
    void createStats_SavesNewStatsToService() {
        // Arrange
        var playerId = new ObjectId().toHexString();

        // Act
        controller.createStats(playerId);

        // Assert
        var argument = ArgumentCaptor.forClass(PlayerStats.class);
        Mockito.verify(statsService).save(argument.capture());
        var actual = argument.getValue();
        Assertions.assertTrue(playerId.equals(actual.getPlayerId().toHexString()));

        Assertions.assertEquals(1, actual.getLevel());
        Assertions.assertEquals(0, actual.getExperiencePoints());

        Assertions.assertEquals(50, actual.getMaxHealth());
        Assertions.assertEquals(50, actual.getCurrentHealth());
        Assertions.assertEquals(20, actual.getMaxEnergy());
        Assertions.assertEquals(20, actual.getCurrentEnergy());

        Assertions.assertEquals(10, actual.getAttack());
        Assertions.assertEquals(5, actual.getDefense());
        Assertions.assertEquals(7, actual.getSpecialAttack());
        Assertions.assertEquals(3, actual.getSpecialDefense());
        Assertions.assertEquals(5, actual.getSpeed());
    }

    @Test
    void updateStats_UpdatesStatsInService() {
        // Arrange
        var expectedStats = new PlayerStats();
        var playerId = new ObjectId();
        expectedStats.setPlayerId(playerId);
        expectedStats.setLevel(99);
        expectedStats.setExperiencePoints(9999);

        expectedStats.setMaxHealth(100);
        expectedStats.setCurrentHealth(37);
        expectedStats.setMaxEnergy(50);
        expectedStats.setCurrentEnergy(17);

        expectedStats.setAttack(9);
        expectedStats.setDefense(8);
        expectedStats.setSpecialAttack(7);
        expectedStats.setSpecialDefense(6);
        expectedStats.setSpeed(5);

        Mockito.when(statsService.exists(playerId)).thenReturn(true);

        // Act
        controller.updateStats(playerId.toHexString(), expectedStats);

        // Assert
        var argument = ArgumentCaptor.forClass(PlayerStats.class);
        Mockito.verify(statsService).save(argument.capture());
        var actual = argument.getValue();

        Assertions.assertEquals(playerId, actual.getPlayerId());
        Assertions.assertEquals(100, actual.getMaxHealth());
        Assertions.assertEquals(37, actual.getCurrentHealth());
        Assertions.assertEquals(50, actual.getMaxEnergy());
        Assertions.assertEquals(17, actual.getCurrentEnergy());
        Assertions.assertEquals(9, actual.getAttack());
        Assertions.assertEquals(8, actual.getDefense());
        Assertions.assertEquals(7, actual.getSpecialAttack());
        Assertions.assertEquals(6, actual.getSpecialDefense());
        Assertions.assertEquals(5, actual.getSpeed());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void updateStats_Throws_IfPlayerIdIsNullOrEmptyString(boolean isNull) {
        // Arrange
        var id = isNull ? null : "";

        // Act
        var ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> controller.updateStats(id, new PlayerStats()));

        // Assert
        Assertions.assertTrue(ex.getMessage().contains("playerID"));
    }

    @Test
    public void updateStats_Throws_IfPlayerIsntInStatsService() {
        // Arrange
        var stats = new PlayerStats();
        var playerId = new ObjectId();
        stats.setPlayerId(playerId);

        // Act
        var ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> controller.updateStats(new ObjectId().toHexString(), stats));

        // Assert
        Assertions.assertTrue(ex.getMessage().contains("No stats found"));
    }
}
