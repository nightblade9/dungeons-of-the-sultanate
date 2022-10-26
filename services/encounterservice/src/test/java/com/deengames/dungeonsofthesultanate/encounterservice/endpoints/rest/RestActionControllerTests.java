package com.deengames.dungeonsofthesultanate.encounterservice.endpoints.rest;

import com.deengames.dungeonsofthesultanate.encounterservice.client.ServiceToServiceClient;
import com.deengames.dungeonsofthesultanate.encounterservice.dtos.PlayerStatsDto;
import com.deengames.dungeonsofthesultanate.encounterservice.endpoints.encounters.EncounterController;
import com.deengames.dungeonsofthesultanate.encounterservice.endpoints.encounters.Location;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;

import java.util.Collection;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
public class RestActionControllerTests {

    @Autowired
    private RestActionController controller;

    @MockBean
    private ServiceToServiceClient client;

    @Mock
    private Environment environment;

    @Test
    void rest_RestsAndHeals_IfPlayerHasTurns() {
        // Arrange
        var playerId = UUID.randomUUID().toString();

        // Check for turns left => yup
        Mockito.when(client.patch(anyString(), eq(playerId), eq(Boolean.class)))
            .thenReturn(true);

        var playerStats = new PlayerStatsDto();
        playerStats.setCurrentHealth(0);
        playerStats.setCurrentEnergy(0);

        Mockito.when(client.get(anyString(), eq(PlayerStatsDto.class)))
                .thenReturn(playerStats);

        // Act
        var result = controller.rest(playerId);

        // Assert
        Assertions.assertTrue(result.contains("quick nap"));

        var argument = ArgumentCaptor.forClass(PlayerStatsDto.class);
        Mockito.verify(client).put(anyString(), argument.capture(), eq(String.class));

        var actual = argument.getValue();
        Assertions.assertEquals(actual.getMaxHealth(), actual.getCurrentHealth());
        Assertions.assertEquals(actual.getMaxEnergy(), actual.getCurrentEnergy());
    }

    @Test
    void rest_DoesNotRestAndHeal_IfPlayerIsOutOfTurns() {
        // Arrange
        var playerId = UUID.randomUUID().toString();
        // Check for turns left => nope
        Mockito.when(client.patch(anyString(), eq(playerId), eq(Boolean.class)))
                .thenReturn(false);

        // Act
        var result = controller.rest(playerId);

        // Assert
        Assertions.assertTrue(result.contains("don't have any turns left"));
    }
}
