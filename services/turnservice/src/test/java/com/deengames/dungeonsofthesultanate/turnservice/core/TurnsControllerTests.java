package com.deengames.dungeonsofthesultanate.turnservice.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

@SpringBootTest
public class TurnsControllerTests {

    @Autowired
    private TurnsController turnsController; // Subject under test

    @MockBean
    private TurnService turnService;

    @Test
    public void createPlayer_AddsPlayerToService() {
        // Nothing to arrange
        var expectedId = UUID.randomUUID().toString();

        // Act
        turnsController.createTurns(expectedId);

        // Assert
        var argument = ArgumentCaptor.forClass(PlayerTurns.class);
        Mockito.verify(turnService).save(argument.capture());
        var actual = argument.getValue();
        Assertions.assertEquals(expectedId, actual.getUserId());
        Assertions.assertEquals(PlayerTurns.newPlayerNumTurns, actual.getNumTurns());
    }

    @Test
    public void getPlayer_ReturnsPlayerTurnsFromService() {
        // Arrange
        var expectedId = UUID.randomUUID().toString();
        var expectedTurns = 39;
        var expected = new PlayerTurns(expectedId, expectedTurns);

        Mockito.when(turnService.getTurns(expectedId))
            .thenReturn(expected);

        // Act
        var actual = turnsController.getTurns(expectedId);

        Assertions.assertEquals(expectedTurns, actual);
    }

    @Test
    public void getTurns_ReturnsZero_ifPlayerIsntInService() {
        // Act
        var actual = turnsController.getTurns("no such id");
        // Assert
        Assertions.assertEquals(0, actual);
    }

    @Test
    void consumeTurn_Throws_IfServiceReturrnsNull() {
        // Arrange
        var userId = "hello, world!";
        Mockito.when(turnService.getTurns(userId)).thenReturn(null);

        // Act
        var actual = Assertions.assertThrows(IllegalStateException.class,
                () -> turnsController.consumeTurn(userId));

        Assertions.assertNotNull(actual);
    }

    @ParameterizedTest
    @ValueSource(ints={-192, -1, 0})
    void consumeTurn_ReturnsFalse_IfPlayerHasNoTurnsLeft(int numTurns) {
        // Arrange
        var userId = "hello, world!";
        var turns = new PlayerTurns(userId, numTurns);
        Mockito.when(turnService.getTurns(userId)).thenReturn(turns);

        // Act
        var actual = turnsController.consumeTurn(userId);

        Assertions.assertEquals(numTurns, turns.getNumTurns());
        Assertions.assertFalse(actual);
    }

    @Test
    void consumeTurn_ReturnsTruAndDecreasesTurns() {
        // Arrange
        var userId = "positive_test_case";
        var turns = new PlayerTurns(userId, 99);
        Mockito.when(turnService.getTurns(userId)).thenReturn(turns);

        // Act
        var actual = turnsController.consumeTurn(userId);

        Assertions.assertEquals(98, turns.getNumTurns());
        Assertions.assertTrue(actual);
    }


}
