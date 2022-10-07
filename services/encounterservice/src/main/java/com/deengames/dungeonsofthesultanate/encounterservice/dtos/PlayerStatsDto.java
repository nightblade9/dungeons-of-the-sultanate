package com.deengames.dungeonsofthesultanate.encounterservice.dtos;

import com.deengames.dungeonsofthesultanate.encounterservice.battle.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter
public class PlayerStatsDto extends BaseEntity {

    private String playerId;
    private int level = 1;
    private int experiencePoints = 0;
}
