package com.deengames.dungeonsofthesultanate.turnservice.core;

import com.deengames.dungeonsofthesultanate.turnservice.BaseIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerControllerIntegrationTests extends BaseIntegrationTest {

    // Subject under test
    @Autowired
    private PlayerController controller;

    @Autowired
    private TurnRepository turnRepository;

    @Test
    public void createPlayer_InsertsRecordIntoDatabase() {
        // Arrange
        var expectedId = UUID.randomUUID().toString();

        // Act
        controller.createPlayer(expectedId);

        // Assert
        var actual = turnRepository.findById(expectedId).get();
        assertEquals(expectedId, actual.getUserId());
        assertEquals(PlayerTurns.newPlayerNumTurns, actual.getNumTurns());
    }

    @Test
    public void createPlayer_DoesNotCreateDuplicateRecord_IfPlayerIsAlreadyInDatabase()
    {
        // Arrange
        var expectedId = UUID.randomUUID().toString();
        var existingPlayer = new PlayerTurns();
        existingPlayer.setUserId(expectedId);
        turnRepository.save(existingPlayer);

        // Act
        controller.createPlayer(expectedId);

        // Assert
        var actuals = turnRepository.findAll();
        Assertions.assertEquals(((Collection<?>)actuals).size(), 1);
    }
}