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
public class PlayerControllerTests {

    @Autowired
    private PlayerController playerController; // Subject under test

    @MockBean
    private TurnService turnService;

    @Test
    public void createPlayer_AddsPlayerToService() {
        // Nothing to arrange
        var expectedId = UUID.randomUUID().toString();

        // Act
        playerController.createPlayer(expectedId);

        var argument = ArgumentCaptor.forClass(PlayerTurns.class);
        Mockito.verify(turnService).addNewRecord(argument.capture());
        var actual = argument.getValue();
        Assertions.assertEquals(actual.getUserId(), expectedId);
        Assertions.assertEquals(actual.getNumTurns(), PlayerTurns.newPlayerNumTurns);
    }

    @Test
    public void getPlayer_returnsPlayerTurnsFromService() {
        // Arrange
        var expectedId = UUID.randomUUID().toString();
        var expectedTurns = 39;

        Mockito.when(turnService.getNumTurns(expectedId))
            .thenReturn(expectedTurns);

        // Act
        var actual = playerController.getPlayer(expectedId);

        Assertions.assertEquals(actual, expectedTurns);
    }

    @Test
    public void getPlayer_returnsNull_ifPlayerIsntInService() {
        // Act
        var actual = playerController.getPlayer("no such id");
        // Assert
        Assertions.assertEquals(actual, 0);
    }
}
