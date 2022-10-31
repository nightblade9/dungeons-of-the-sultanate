package com.deengames.dungeonsofthesultanate.encounterservice.monsters;

import com.deengames.dungeonsofthesultanate.encounterservice.battle.BaseEntity;
import lombok.Getter;
import lombok.Setter;

public class Monster extends BaseEntity implements Cloneable {

    // Sigh. Inline instantiation in Java creates subtypes. SMH, Java. SMH.

    public Monster(String name, int health, int energy, int attack, int defense, int specialAttack, int specialDefense, int speed)
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
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
