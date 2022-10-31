package com.deengames.dungeonsofthesultanate.encounterservice.battle;

import com.deengames.dungeonsofthesultanate.encounterservice.dtos.PlayerStatsDto;
import com.deengames.dungeonsofthesultanate.encounterservice.monsters.MonsterFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BattleResolverTests {

    @Test
    void resolve_ResolvesToTheDeathOfTheMonster() throws CloneNotSupportedException {
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
    void resolve_ResolvesToTheDeathOfThePlayer() throws CloneNotSupportedException {
        // Arrange
        var player = new PlayerStatsDto();
        player.setName("Player");
        player.setCurrentHealth(1); // huhuhuhh...

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

    @Test
    void resolve_PlayerAndMOnsterCriticallyAttack_Sometimes() throws CloneNotSupportedException {
        // Arrange
        var player = new PlayerStatsDto();
        player.setCriticalHitRate(0.4f); // makes test more reliable
        player.setName("Player");
        player.setMaxHealth(300);
        player.setCurrentHealth(300);

        var monster = MonsterFactory.create("Grass Slime");
        monster.setMaxHealth(300);
        monster.setCurrentHealth(300);
        // probably won't happen in reality, but worth testing
        monster.setCriticalHitRate(0.25f);

        // Act
        var messages = BattleResolver.resolve(player, monster);

        // Assert
        Assertions.assertTrue(messages.stream().anyMatch(m -> m.contains("Player CRITICALLY attacks")));
        Assertions.assertTrue(messages.stream().anyMatch(m -> m.contains("Player attacks")));
        Assertions.assertTrue(messages.stream().anyMatch(m -> m.contains("Grass Slime CRITICALLY attacks")));
        Assertions.assertTrue(messages.stream().anyMatch(m -> m.contains("Grass Slime attacks")));
    }

}
