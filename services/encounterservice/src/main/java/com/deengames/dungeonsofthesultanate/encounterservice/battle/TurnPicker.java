package com.deengames.dungeonsofthesultanate.encounterservice.battle;

import com.deengames.dungeonsofthesultanate.encounterservice.dtos.PlayerStatsDto;
import com.deengames.dungeonsofthesultanate.encounterservice.monsters.Monster;

public class TurnPicker {

    private final PlayerStatsDto player;
    private final BaseEntity monster;
    private int pointsNeededForTurn = 1000; // reasonable
    private double playerPoints = 0;
    private double monsterPoints = 0;

    public TurnPicker(PlayerStatsDto player, Monster monster) {
        if (player.getSpeed() > pointsNeededForTurn || monster.getSpeed() > pointsNeededForTurn) {
            throw new IllegalStateException(String.format("Player/monster are too fast to calculate turns (%s and %s respectively)", player.getSpeed(), monster.getSpeed()));
        }

        this.player = player;
        this.monster = monster;
    }

    public BaseEntity getNextTurn() {
        // Integer rounding AT ANY STAGE HERE gives us invalid results - even with round, ceil, etc.
        // Float is OK. We will lose some precision, but that's totally fine.
        var playerTimeToTurn = Math.ceil((pointsNeededForTurn - playerPoints) / player.getSpeed());
        var monsterTimeToTurn = Math.ceil((pointsNeededForTurn - monsterPoints) / monster.getSpeed());
        var advanceTimeBy = Math.ceil(Math.min(playerTimeToTurn, monsterTimeToTurn));
        playerPoints += advanceTimeBy * player.getSpeed();
        monsterPoints += advanceTimeBy * monster.getSpeed();

        if (playerPoints >= pointsNeededForTurn) {
            playerPoints -= pointsNeededForTurn;
            return player;
        } else if (monsterPoints >= pointsNeededForTurn) {
            monsterPoints -= pointsNeededForTurn;
            return monster;
        } else {
            throw new IllegalStateException("Time/turn calculation error!");
        }
    }

}
