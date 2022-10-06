package com.deengames.dungeonsofthesultanate.playerservice.stats;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
            .thenReturn(Optional.of(stats));

        // Act
        var actual = controller.getStats(playerId.toHexString());

        // Assert
        Assertions.assertEquals(stats, actual.get());
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
    void updateStats_SavesStatsToService() {
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
        controller.updateStats(expectedStats);

        // Assert
        var argument = ArgumentCaptor.forClass(PlayerStats.class);
        Mockito.verify(statsService).save(argument.capture());
        var actual = argument.getValue();

        Assertions.assertEquals(actual.getMaxHealth(), 100);
        Assertions.assertEquals(actual.getCurrentHealth(), 37);
        Assertions.assertEquals(actual.getMaxEnergy(), 50);
        Assertions.assertEquals(actual.getCurrentEnergy(), 17);
        Assertions.assertEquals(actual.getAttack(), 9);
        Assertions.assertEquals(actual.getDefense(), 8);
        Assertions.assertEquals(actual.getSpecialAttack(), 7);
        Assertions.assertEquals(actual.getSpecialDefense(), 6);
        Assertions.assertEquals(actual.getSpeed(), 5);
    }

    @Test
    void updateStats_Throws_IfPlayerIdIsNull() {
        // Act
        var ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> controller.updateStats(new PlayerStats()));

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
                () -> controller.updateStats(stats));

        // Assert
        Assertions.assertTrue(ex.getMessage().contains("No stats found"));
    }
}
