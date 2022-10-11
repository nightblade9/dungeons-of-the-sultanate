package com.deengames.dungeonsofthesultanate.encounterservice.stats;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document @Getter @Setter
public class PlayerStats {

    @Id
    private ObjectId playerId;
    private int level = 1;
    private int experiencePoints = 0;

    private int maxHealth = 50;
    private int currentHealth = 50;
    private int maxEnergy = 20;
    private int currentEnergy = 20;

    private int attack = 10;
    private int defense = 5;
    private int specialAttack = 7;
    private int specialDefense = 3;
    private int speed = 5;
}
