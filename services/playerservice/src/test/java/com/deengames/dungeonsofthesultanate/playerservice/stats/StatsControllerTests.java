package com.deengames.dungeonsofthesultanate.playerservice.stats;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class StatsControllerTests {

    @Autowired
    private StatsController controller; // thing we're testing

    @MockBean
    private StatsService statsService;

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
        expectedStats.setPlayerId(new ObjectId());
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

        // Act

        // Assert
    }
}
