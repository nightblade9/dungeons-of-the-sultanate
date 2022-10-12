package com.deengames.dungeonsofthesultanate.encounterservice.encounters;

import com.deengames.dungeonsofthesultanate.encounterservice.battle.BattleMonsterPicker;
import com.deengames.dungeonsofthesultanate.encounterservice.battle.BattleResolver;
import com.deengames.dungeonsofthesultanate.encounterservice.client.ServiceToServiceClient;
import com.deengames.dungeonsofthesultanate.encounterservice.dtos.PlayerStatsDto;
import com.deengames.dungeonsofthesultanate.encounterservice.monsters.MonsterFactory;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class EncounterController {

    @Autowired
    private ServiceToServiceClient client;

    @Autowired
    private Environment environment;

    @PostMapping(value = "/encounter", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Collection<String> tryEncounter(@RequestBody JSONObject body) throws Exception {
        // TODO: care about location name. e.g. Towering Tree Forest vs. Wishful Well

        var playerId = body.getAsString("playerId");
        var locationName = body.getAsString("locationName");

        // Did we have enough turns to encounter?
        var turnServiceUrl = String.format("%s/turns", environment.getProperty("dots.serviceToService.turnService"));
        var consumedTurn = client.patch(turnServiceUrl, playerId, Boolean.class);
        if (!consumedTurn) {
            return List.of(new String[]{"No turns left!"});
        }

        // What kind of encounter was it -- battle? random loot?
        var location = Location.TOWERING_TREE_FOREST; // TODO: proper parsing valueOf(locationName);
        var encounterType = EncounterPicker.chooseEncounter(location);
        if (encounterType != EncounterType.BATTLE) {
            throw new IllegalStateException(String.format("Encounters of type %s aren't implemented yet", encounterType));
        }

        // Fight it out!
        var playerServiceUrl = environment.getProperty("dots.serviceToService.playerService");
        var getPlayerUrl = String.format("%s/stats/%s", playerServiceUrl, playerId);
        var player = client.get(getPlayerUrl, PlayerStatsDto.class);

        player.setName("Player");

        var monsterName = BattleMonsterPicker.pickMonster(location);
        var monster = MonsterFactory.create(monsterName);
        var battleLogs = BattleResolver.resolve(player, monster);

       // Persist changes to the player
        var updatePlayerUrl = String.format("%s/stats/%s", playerServiceUrl, player.getId());
       client.put(updatePlayerUrl, player, String.class);

        return battleLogs;
    }

}
