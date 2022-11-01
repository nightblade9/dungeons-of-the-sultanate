package com.deengames.dungeonsofthesultanate.encounterservice.data;

import com.deengames.dungeonsofthesultanate.encounterservice.battle.BaseEntity;
import com.deengames.dungeonsofthesultanate.encounterservice.monsters.Monster;

import java.util.HashMap;

// "static" class (C# meaning)
public class MonsterData extends BaseEntity {

    public static final HashMap<String, Monster> allData = new HashMap<>();

    static {
        allData.put("Grass Slime", new Monster("Grass Slime", 20, 5, 8, 4, 13, 0, 3, 5));
        allData.put("Rock Rabbit", new Monster("Rock Rabbit", 12, 0, 12, 6, 7, 3, 5, 7));
    }
}
