package com.deengames.dungeonsofthesultanate.turnservice.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
        turnsController.createPlayer(expectedId);

        var argument = ArgumentCaptor.forClass(PlayerTurns.class);
        Mockito.verify(turnService).addNewRecord(argument.capture());
        var actual = argument.getValue();
        Assertions.assertEquals(actual.getUserId(), expectedId);
        Assertions.assertEquals(actual.getNumTurns(), PlayerTurns.newPlayerNumTurns);
    }

    @Test
    public void getPlayer_ReturnsPlayerTurnsFromService() {
        // Arrange
        var expectedId = UUID.randomUUID().toString();
        var expectedTurns = 39;

        Mockito.when(turnService.getNumTurns(expectedId))
            .thenReturn(expectedTurns);

        // Act
        var actual = turnsController.getPlayer(expectedId);

        Assertions.assertEquals(actual, expectedTurns);
    }

    @Test
    public void getPlayer_ReturnsNull_ifPlayerIsntInService() {
        // Act
        var actual = turnsController.getPlayer("no such id");
        // Assert
        Assertions.assertEquals(actual, 0);
    }
}
