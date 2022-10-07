package com.deengames.dungeonsofthesultanate.encounterservice.battle;

import com.deengames.dungeonsofthesultanate.encounterservice.dtos.PlayerStatsDto;
import com.deengames.dungeonsofthesultanate.encounterservice.monsters.MonsterFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BattleResolverTests {

    @Test
    void resolve_ResolvesToTheDeathOfTheMonster() {
        // Arrange
        var player = new PlayerStatsDto();
        player.setName("Player");
        var monster = MonsterFactory.create("Grass Slime");

        // Act
        var messages = BattleResolver.resolve(player, monster);

        // Assert
        Assertions.assertEquals(0, monster.getCurrentHealth());
        Assertions.assertTrue(player.getCurrentHealth() < player.getMaxHealth());
        Assertions.assertTrue(messages.size() > 0);
        Assertions.assertTrue(messages.stream().anyMatch(m -> m.contains("Grass Slime attacks Player")));
        Assertions.assertTrue(messages.stream().anyMatch(m -> m.contains("Player attacks Grass Slime")));
        Assertions.assertTrue(messages.stream().anyMatch(m -> m.contains("vanquish")));
    }

    @Test
    void resolve_ResolvesToTheDeathOfThePlayer() {
        // Arrange
        var player = new PlayerStatsDto();
        player.setName("Player");
        player.setCurrentHealth(5); // huhuhuhh...

        var monster = MonsterFactory.create("Grass Slime");

        // Act
        var messages = BattleResolver.resolve(player, monster);

        // Assert
        Assertions.assertEquals(0, player.getCurrentHealth());
        Assertions.assertTrue(monster.getCurrentHealth() < monster.getMaxHealth());
        Assertions.assertTrue(messages.size() > 0);
        Assertions.assertTrue(messages.stream().anyMatch(m -> m.contains("Grass Slime attacks Player")));
        Assertions.assertTrue(messages.stream().anyMatch(m -> m.contains("Player attacks Grass Slime")));
        Assertions.assertTrue(messages.stream().anyMatch(m -> m.contains("defeated")));
    }

}
