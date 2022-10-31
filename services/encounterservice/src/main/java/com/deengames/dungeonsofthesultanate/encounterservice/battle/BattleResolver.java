package com.deengames.dungeonsofthesultanate.encounterservice.battle;

import com.deengames.dungeonsofthesultanate.encounterservice.dtos.PlayerStatsDto;
import com.deengames.dungeonsofthesultanate.encounterservice.monsters.Monster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class BattleResolver {

    private static final Random random = new Random();
    private static final float DAMAGE_VARIATION_PERCENT = 0.2f;
    private static final float CRITICAL_DAMAGE_RATE = 1.5f;

    private BattleResolver() { } // we're a "static" class (C# meaning)

    public static Collection<String> resolve(PlayerStatsDto player, Monster monster) {
        var battleLogs = new ArrayList<String>();
        var maxRounds = 100;
        var turnPicker = new TurnPicker(player, monster);

        while (player.getCurrentHealth() > 0 && monster.getCurrentHealth() > 0 && maxRounds-- > 0)
        {
            var nextTurn = turnPicker.getNextTurn();
            var battleLog = attacks(nextTurn, nextTurn == player ? monster : player);
            battleLogs.add(battleLog);
        }

        if (player.getCurrentHealth() == 0)
        {
            battleLogs.add("You slink away, defeated.");
        }
        else if (monster.getCurrentHealth() == 0)
        {
            battleLogs.add(String.format("You vanquish the %s!", monster.getName()));
        }
        else if (maxRounds == 0)
        {
            battleLogs.add("You shrug and walk away.");
        }
        else
        {
            throw new IllegalStateException("Unknown battle resolution");
        }

        return battleLogs;
    }

    public static String attacks(BaseEntity attacker, BaseEntity defender) {
        // Calculate
        var baseDamage = attacker.getAttack() - defender.getDefense();
        var variance = DAMAGE_VARIATION_PERCENT / 2;
        var minDamage = (int)Math.max(0, (1 - variance) * baseDamage);
        var maxDamage = (int)Math.max(0, (1 + variance) * baseDamage);
        // +1 here makes these values inclusive, and prevents an exception when min = max
        var damage = random.ints(minDamage, maxDamage + 1).findAny().getAsInt();
        var isCritical = false;

        if (random.nextFloat() <= attacker.getCriticalHitRate())
        {
            isCritical = true;
            damage = damage * Math.round(1 + CRITICAL_DAMAGE_RATE);
        }

        // Apply
        defender.setCurrentHealth(Math.max(0, defender.getCurrentHealth() - damage));

        var attackString = isCritical ? "CRITICALLY attacks" : "attacks";
        return String.format("%s %s %s for %s damage!", attacker.getName(), attackString, defender.getName(), damage);
    }
}
