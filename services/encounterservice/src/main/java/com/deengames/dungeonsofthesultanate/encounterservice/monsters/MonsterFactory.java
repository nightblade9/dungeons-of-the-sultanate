package com.deengames.dungeonsofthesultanate.encounterservice.monsters;

public class MonsterFactory {

    public static Monster create(String monsterName) {
        // TODO: look up stats in the DB?
        var monster = new Monster();
        monster.setName(monsterName);
        monster.setMaxHealth(20);
        monster.setCurrentHealth(monster.getMaxHealth());
        monster.setMaxEnergy(5);
        monster.setCurrentEnergy(monster.getMaxEnergy());
        monster.setAttack(8);
        monster.setDefense(4);
        monster.setSpecialAttack(13);
        monster.setSpecialDefense(0);
        monster.setSpeed(3);
        return monster;
    }
}
