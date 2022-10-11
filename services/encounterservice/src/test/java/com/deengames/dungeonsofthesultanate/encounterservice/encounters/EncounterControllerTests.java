package com.deengames.dungeonsofthesultanate.encounterservice.encounters;

import com.deengames.dungeonsofthesultanate.encounterservice.client.ServiceToServiceClient;
import com.deengames.dungeonsofthesultanate.encounterservice.dtos.PlayerStatsDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
public class EncounterControllerTests {

    @Autowired
    private EncounterController controller;

    @MockBean
    private ServiceToServiceClient client;

    @Mock
    private Environment environment;

    @Test
    void tryEncounter_ReturnsNoTurnsMessage_IfPlayerHasNoTurnsLeft() {
        // Arrange
        var playerId = UUID.randomUUID().toString();
        // Check for turns left => nope
        Mockito.when(client.patch(anyString(), eq(playerId), eq(Boolean.class)))
            .thenReturn(false);

        // Act
        var actuals = controller.tryEncounter(playerId, "Sleepy Hollow");

        // Assert
        Assertions.assertEquals(actuals.size(), 1);
        var actual = actuals.stream().findFirst().get();
        Assertions.assertEquals("No turns left!", actual);
    }

    @Test
    void tryEncounter_ReturnsMessagesAndUpdatesPlayer_IfBattleProceeds() {
        // Arrange
        var playerId = UUID.randomUUID().toString();

        // Check for turns left => yup
        Mockito.when(client.patch(anyString(), eq(playerId), eq(Boolean.class)))
                .thenReturn(true);

        var playerStats = new PlayerStatsDto();

        // Get player stats => returns test stats
        Mockito.when(client.get(anyString(), eq(PlayerStatsDto.class)))
                .thenReturn(playerStats);

        // Act
        var actuals = controller.tryEncounter(playerId, Location.TOWERING_TREE_FOREST.name());

        // Assert
        Assertions.assertTrue(actuals.stream().anyMatch(m -> m.contains("Player attacks")));
        Assertions.assertTrue(actuals.stream().anyMatch(m -> m.contains("attacks Player")));
        Assertions.assertTrue(playerStats.getCurrentHealth() < playerStats.getMaxHealth());
        // Verify player stats were PUT back to the player service
        Mockito.verify(client, Mockito.times(1)).put(anyString(), eq(playerStats), eq(String.class));
    }
}
