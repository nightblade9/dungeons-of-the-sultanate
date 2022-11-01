package com.deengames.dungeonsofthesultanate.encounterservice.monsters;

import com.deengames.dungeonsofthesultanate.encounterservice.battle.BaseEntity;
import lombok.Getter;
import lombok.Setter;

public class Monster extends BaseEntity implements Cloneable {

    @Getter
    private int experiencePoints = 0;

    public Monster(String name, int health, int energy, int attack, int defense, int specialAttack, int specialDefense, int speed, int experiencePoints)
    {
        this.setName(name);
        this.setMaxHealth(health);
        this.setCurrentHealth(health);
        this.setMaxEnergy(energy);
        this.setCurrentEnergy(energy);
        this.setAttack(attack);
        this.setSpecialAttack(specialAttack);
        this.setDefense(defense);
        this.setSpecialDefense(specialDefense);
        this.setSpeed(speed);
        this.experiencePoints = experiencePoints;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
