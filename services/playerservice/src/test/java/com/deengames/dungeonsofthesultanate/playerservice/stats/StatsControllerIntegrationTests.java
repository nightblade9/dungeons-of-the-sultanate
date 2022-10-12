package com.deengames.dungeonsofthesultanate.playerservice.stats;

import com.deengames.dungeonsofthesultanate.playerservice.BaseIntegrationTest;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// Integration-ish; we mock service-to-service calls.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatsControllerIntegrationTests extends BaseIntegrationTest {

    // Subject under test
    @Autowired
    private StatsController controller;

    @Test
    void getStats_GetsStatsFromDatabase_IfStatsExistInDatabase() {
        // Arrange
        var existingId = new ObjectId();
        var expectedStats = new PlayerStats();
        expectedStats.setPlayerId(existingId);
        // It's pure JPA, don't need to test all the fields
        expectedStats.setCurrentHealth(9999);
        expectedStats.setAttack(999);
        expectedStats.setSpecialDefense(99);
        statsRepository.save(expectedStats);

        // Act
        var actualStats = controller.getStats(existingId.toHexString());

        // Assert
        Assertions.assertEquals(expectedStats.getPlayerId(), actualStats.getPlayerId());
        Assertions.assertEquals(expectedStats.getCurrentHealth(), actualStats.getCurrentHealth());
        Assertions.assertEquals(expectedStats.getAttack(), actualStats.getAttack());
        Assertions.assertEquals(expectedStats.getSpecialDefense(), actualStats.getSpecialDefense());
    }

    @Test
    public void createStats_InsertsStatsIntoDatabase() {
        // Arrange
        var userId = new ObjectId();

        // Act
        controller.createStats(userId.toHexString());

        // Assert
        var result = statsRepository.findById(userId.toHexString());
        Assertions.assertTrue(result.isPresent());
        var actual = result.get();
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
    public void updateStats_UpdatesStats() {
        // Arrange
        var playerId = new ObjectId();
        var stats = new PlayerStats();
        stats.setPlayerId(playerId);
        statsRepository.save(stats);

        stats.setMaxHealth(999);
        stats.setCurrentHealth(888);
        stats.setMaxEnergy(99);
        stats.setCurrentEnergy(88);
        stats.setAttack(77);
        stats.setDefense(75);
        stats.setSpecialAttack(73);
        stats.setSpecialDefense(71);
        stats.setSpeed(70);

        // Act
        controller.updateStats(playerId.toHexString(), stats);

        // Assert
        var actual = statsRepository.findById(playerId.toHexString()).get();
        Assertions.assertEquals(actual.getMaxHealth(), 999);
        Assertions.assertEquals(actual.getCurrentHealth(), 888);
        Assertions.assertEquals(actual.getMaxEnergy(), 99);
        Assertions.assertEquals(actual.getCurrentEnergy(), 88);
        Assertions.assertEquals(actual.getAttack(), 77);
        Assertions.assertEquals(actual.getDefense(), 75);
        Assertions.assertEquals(actual.getSpecialAttack(), 73);
        Assertions.assertEquals(actual.getSpecialDefense(), 71);
        Assertions.assertEquals(actual.getSpeed(), 70);
    }
}