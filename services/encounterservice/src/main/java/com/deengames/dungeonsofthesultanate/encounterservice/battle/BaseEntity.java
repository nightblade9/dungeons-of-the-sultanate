package com.deengames.dungeonsofthesultanate.encounterservice.battle;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class BaseEntity {

    private String name;
    private int maxHealth = 50;
    private int currentHealth = 50;
    private int maxEnergy = 20;
    private int currentEnergy = 20;

    private int attack = 10;
    private int defense = 5;
    private int specialAttack = 7;
    private int specialDefense = 3;
    private int speed = 5;
    private float criticalHitRate = 0.0f; // NOT subject to change!

}
