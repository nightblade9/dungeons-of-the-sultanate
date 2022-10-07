package com.deengames.dungeonsofthesultanate.encounterservice.dtos;

import com.deengames.dungeonsofthesultanate.encounterservice.battle.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PlayerStatsDto extends BaseEntity {

    private String playerId;
    private int level = 1;
    private int experiencePoints = 0;
}
