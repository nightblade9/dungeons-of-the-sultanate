package com.deengames.dungeonsofthesultanate.encounterservice.battle;

import com.deengames.dungeonsofthesultanate.encounterservice.dtos.PlayerStatsDto;
import com.deengames.dungeonsofthesultanate.encounterservice.monsters.Monster;
import com.deengames.dungeonsofthesultanate.encounterservice.monsters.MonsterFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TurnPickerTests {

    @Test
    void getNextTurn_returnsMultiplePlayerTurns_IfPLayerIsMultipleTimesFaster() {
        // Arrange
        var player = new PlayerStatsDto();
        player.setSpeed(100);
        var monster = MonsterFactory.create("test monster");
        monster.setSpeed(player.getSpeed() / 2);
        var turnPicker = new TurnPicker(player, monster);
        var actual = new ArrayList<BaseEntity>();

        // Act
        for (var i = 0; i < 12; i++) {
            var next = turnPicker.getNextTurn();
            actual.add(next);
        }

        // Assert
        Assertions.assertEquals(8, actual.stream().filter(b -> b == player).count());
        Assertions.assertEquals(4, actual.stream().filter(b -> b == monster).count());
    }

    @Test
    void getNextTurn_returnsInterleavedTurnsForImperfectMultiple_IfMonsterIsFaster() {
        // Arrange
        var player = new PlayerStatsDto();
        player.setSpeed(3);
        var monster = MonsterFactory.create("test monster");
        monster.setSpeed(5);
        var turnPicker = new TurnPicker(player, monster);
        var actual = new ArrayList<BaseEntity>();

        // Act
        for (var i = 0; i < 100; i++) {
            var next = turnPicker.getNextTurn();
            actual.add(next);
        }

        // Assert
        Assertions.assertEquals(63, actual.stream().filter(b -> b == monster).count());
        Assertions.assertEquals(37, actual.stream().filter(b -> b == player).count());
    }
}
