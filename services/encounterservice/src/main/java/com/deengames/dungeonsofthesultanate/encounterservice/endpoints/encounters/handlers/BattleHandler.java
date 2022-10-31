package com.deengames.dungeonsofthesultanate.encounterservice.endpoints.encounters.handlers;

import com.deengames.dungeonsofthesultanate.encounterservice.battle.BattleMonsterPicker;
import com.deengames.dungeonsofthesultanate.encounterservice.battle.BattleResolver;
import com.deengames.dungeonsofthesultanate.encounterservice.dtos.PlayerStatsDto;
import com.deengames.dungeonsofthesultanate.encounterservice.endpoints.encounters.Location;
import com.deengames.dungeonsofthesultanate.encounterservice.monsters.MonsterFactory;
import net.minidev.json.JSONObject;

import java.util.HashMap;

public class BattleHandler implements EncounterHandler {

    @Override
    public JSONObject handle(HashMap<String, Object> inputs) throws CloneNotSupportedException {
        // Fight it out!
        var player = (PlayerStatsDto)inputs.get("player");
        var location = (Location)inputs.get("location");

        player.setName("Player");
        var monsterName = BattleMonsterPicker.pickMonster(location);
        var monster = MonsterFactory.create(monsterName);
        var battleLogs = BattleResolver.resolve(player, monster);
        var imageName = monsterName.toLowerCase().replace(' ', '-');

        var result = new JSONObject();
        result.put("title", String.format("%s battle", monsterName));
        result.put("monster", monsterName);
        result.put("imageName", imageName);
        result.put("logs", battleLogs);
        return result;
    }

}
