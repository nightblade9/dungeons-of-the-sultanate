package com.deengames.dungeonsofthesultanate.encounterservice.endpoints.rest;

import com.deengames.dungeonsofthesultanate.encounterservice.client.ServiceToServiceClient;
import com.deengames.dungeonsofthesultanate.encounterservice.dtos.PlayerStatsDto;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestActionController
{
    @Autowired
    private ServiceToServiceClient client;

    @Autowired
    private Environment environment;

    @GetMapping(value = "/rest", consumes = MediaType.APPLICATION_JSON_VALUE)
    // returns true if rested
    public String rest(@RequestParam String playerId) {
        var turnServiceUrl = environment.getProperty("dots.serviceToService.turnService");
        var consumedTurn = client.patch(String.format("%s/turns", turnServiceUrl), playerId, Boolean.class);

        if (consumedTurn) {
            // Grab the player
            var playerServiceUrl = environment.getProperty("dots.serviceToService.playerService");
            var getPlayerUrl = String.format("%s/stats/%s", playerServiceUrl, playerId);
            var player = client.get(getPlayerUrl, PlayerStatsDto.class);

            // Fill 'er up
            player.setCurrentHealth(player.getMaxHealth());
            player.setCurrentEnergy(player.getMaxEnergy());

            var updatePlayerUrl = String.format("%s/stats/%s", playerServiceUrl, playerId);
            client.put(updatePlayerUrl, player, String.class);
        }

        var message = consumedTurn ?
                "You take a quick nap and wake up feeling refreshed." :
                "You don't have any turns left!";

        return message;
    }
}
