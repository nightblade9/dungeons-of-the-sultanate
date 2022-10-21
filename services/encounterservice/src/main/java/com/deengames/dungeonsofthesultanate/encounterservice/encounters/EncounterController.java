package com.deengames.dungeonsofthesultanate.encounterservice.encounters;

import com.deengames.dungeonsofthesultanate.encounterservice.battle.BattleMonsterPicker;
import com.deengames.dungeonsofthesultanate.encounterservice.battle.BattleResolver;
import com.deengames.dungeonsofthesultanate.encounterservice.client.ServiceToServiceClient;
import com.deengames.dungeonsofthesultanate.encounterservice.dtos.PlayerStatsDto;
import com.deengames.dungeonsofthesultanate.encounterservice.encounters.handlers.BattleHandler;
import com.deengames.dungeonsofthesultanate.encounterservice.encounters.handlers.EncounterHandler;
import com.deengames.dungeonsofthesultanate.encounterservice.monsters.MonsterFactory;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class EncounterController {

    @Autowired
    private ServiceToServiceClient client;

    @Autowired
    private Environment environment;

    private static HashMap<EncounterType, EncounterHandler> handlers = new HashMap<EncounterType, EncounterHandler>() {
        {
            put(EncounterType.BATTLE, new BattleHandler());
        }
    };

    @PostMapping(value = "/encounter", consumes = MediaType.APPLICATION_JSON_VALUE)
    public JSONObject tryEncounter(@RequestBody JSONObject body) {
        // TODO: care about location name. e.g. Towering Tree Forest vs. Wishful Well

        var playerId = body.getAsString("playerId");

        // Did we have enough turns to encounter?
        var turnServiceUrl = String.format("%s/turns", environment.getProperty("dots.serviceToService.turnService"));
        var consumedTurn = client.patch(turnServiceUrl, playerId, Boolean.class);
        if (Boolean.FALSE.equals(consumedTurn)) {
            var result = new JSONObject();
            result.put("logs", new String[]{"No turns left!"});
            return result;
        }

        // What kind of encounter was it -- battle? random loot?
        var location = Location.TOWERING_TREE_FOREST; // TODO: proper parsing;
        var encounterType = EncounterPicker.chooseEncounter(location);
        if (!handlers.containsKey(encounterType)) {
            throw new IllegalStateException(String.format("Encounters of type %s aren't implemented yet", encounterType));
        }

        var inputs = new HashMap<String, Object>();
        inputs.put("location", location);

        // Grab the player for the handler
        var playerServiceUrl = environment.getProperty("dots.serviceToService.playerService");
        var getPlayerUrl = String.format("%s/stats/%s", playerServiceUrl, playerId);
        var player = client.get(getPlayerUrl, PlayerStatsDto.class);
        inputs.put("player", player);

        // Delegate the actual processing to this bad boi
        var result = handlers.get(encounterType).handle(inputs);
        result.put("encounterType", encounterType.toString().toLowerCase());

        // Persist changes to the player
        var updatePlayerUrl = String.format("%s/stats/%s", playerServiceUrl, player.getId());
        client.put(updatePlayerUrl, player, String.class);

        return result;
    }

}
