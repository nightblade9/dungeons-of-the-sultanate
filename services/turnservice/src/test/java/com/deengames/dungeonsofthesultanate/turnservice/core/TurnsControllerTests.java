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

        var argument = ArgumentCaptor.forClass(PlayerTurns.class);
        Mockito.verify(turnService).save(argument.capture());
        var actual = argument.getValue();
        Assertions.assertEquals(actual.getUserId(), expectedId);
        Assertions.assertEquals(actual.getNumTurns(), PlayerTurns.newPlayerNumTurns);
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

        Assertions.assertEquals(actual, expectedTurns);
    }

    @Test
    public void getTurns_ReturnsZero_ifPlayerIsntInService() {
        // Act
        var actual = turnsController.getTurns("no such id");
        // Assert
        Assertions.assertEquals(actual, 0);
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
    void consumeTurn_DoesNothing_IfPlayerHasNoTurnsLeft(int numTurns) {
        // Arrange
        var userId = "hello, world!";
        var turns = new PlayerTurns(userId, numTurns);
        Mockito.when(turnService.getTurns(userId)).thenReturn(turns);

        // Act
        turnsController.consumeTurn(userId);

        Assertions.assertEquals(turns.getNumTurns(), numTurns);
    }

    @Test
    void consumeTurn_DecreasesTurns() {
        // Arrange
        var userId = "positive_test_case";
        var turns = new PlayerTurns(userId, 99);
        Mockito.when(turnService.getTurns(userId)).thenReturn(turns);

        // Act
        turnsController.consumeTurn(userId);

        Assertions.assertEquals(turns.getNumTurns(), 98);
    }


}
