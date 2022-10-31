package com.deengames.dungeonsofthesultanate.encounterservice.monsters;

import com.deengames.dungeonsofthesultanate.encounterservice.data.MonsterData;

public class MonsterFactory {

    public static Monster create(String monsterName) throws CloneNotSupportedException {
        return (Monster)MonsterData.allData.get(monsterName).clone();
    }
}
