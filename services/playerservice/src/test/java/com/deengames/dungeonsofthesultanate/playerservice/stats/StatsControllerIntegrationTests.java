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
}