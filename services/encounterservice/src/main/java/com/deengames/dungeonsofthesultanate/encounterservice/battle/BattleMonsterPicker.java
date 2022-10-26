package com.deengames.dungeonsofthesultanate.encounterservice.battle;

import com.deengames.dungeonsofthesultanate.encounterservice.endpoints.encounters.Location;


public class BattleMonsterPicker {
    public static String pickMonster(Location location) {
        var chance = Math.random();
        // TODO: look these up in a DB and return an enum? Stats? Something?
        return chance <= 0.8 ? "Grass Slime" : "Stone Rabbit";
    }
}
