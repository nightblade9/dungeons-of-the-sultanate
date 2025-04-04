package com.deengames.dungeonsofthesultanate.turnservice.core;

import com.deengames.dungeonsofthesultanate.turnservice.BaseIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TurnsControllerIntegrationTests extends BaseIntegrationTest {

    // Subject under test
    @Autowired
    private TurnsController controller;

    @Autowired
    private TurnRepository turnRepository;

    @Test
    public void createPlayer_InsertsRecordIntoDatabase() {
        // Arrange
        var expectedId = UUID.randomUUID().toString();

        // Act
        controller.createTurns(expectedId);

        // Assert
        var actual = turnRepository.findById(expectedId).get();
        assertEquals(actual.getUserId(), expectedId);
        assertEquals(actual.getNumTurns(), PlayerTurns.newPlayerNumTurns);
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
        controller.createTurns(expectedId);

        // Assert
        var actuals = turnRepository.findAll();
        Assertions.assertEquals(1, ((Collection<?>)actuals).size());
    }

    @Test
    public void getPlayer_GetsPlayerFromDatabase() {
        // Arrange
        var expectedId = "some object id";
        var expectedTurns = 123;
        var expected = new PlayerTurns();
        expected.setUserId(expectedId);
        expected.setNumTurns(expectedTurns);
        turnRepository.save(expected);

        // Act
        var actual = controller.getTurns(expectedId);

        // Assert
        Assertions.assertEquals(expectedTurns, actual);
    }

    @Test
    void consumeTurn_ConsumesTurnAndReturnsTrue() {
        // Arrange
        var playerId = "some object id";
        var expectedTurns = 123;
        var expected = new PlayerTurns();
        expected.setUserId(playerId);
        expected.setNumTurns(expectedTurns + 1);
        turnRepository.save(expected);

        // Act
        var actual = controller.consumeTurn(playerId);

        // Assert
        Assertions.assertTrue(actual);
    }
}