package com.deengames.dungeonsofthesultanate.encounterservice.battle;

import com.deengames.dungeonsofthesultanate.encounterservice.dtos.PlayerStatsDto;
import com.deengames.dungeonsofthesultanate.encounterservice.monsters.Monster;
import com.deengames.dungeonsofthesultanate.encounterservice.monsters.MonsterFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class BattleResolver {

    private static final Random random = new Random();
    private static final float DAMAGE_VARIATION_PERCENT = 0.2f;

    public static Collection<String> resolve(PlayerStatsDto player, Monster monster) {
        var battleLogs = new ArrayList<String>();
        var maxRounds = 100;

        while (player.getCurrentHealth() > 0 && monster.getCurrentHealth() > 0 && maxRounds-- > 0)
        {
            // TODO: implement turns in accordance to speed
            if (player.getSpeed() > monster.getSpeed()) {
                battleLogs.add(attacks(player, monster));
            } else {
                battleLogs.add(attacks(monster, player));
            }
        }

        if (maxRounds == 0)
        {
            battleLogs.add("You shrug and walk away.");
        }

        return battleLogs;
    }

    public static String attacks(BaseEntity attacker, BaseEntity defender) {
        // Calcuate
        var baseDamage = attacker.getAttack() - defender.getDefense();
        var variance = DAMAGE_VARIATION_PERCENT / 2;
        var minDamage = (int)Math.max(0, (1 - variance) * baseDamage);
        var maxDamage = (int)Math.max(0, (1 + variance) * baseDamage);
        var damage = random.nextInt(minDamage, maxDamage);

        // Apply
        defender.setCurrentHealth(Math.max(0, defender.getCurrentHealth() - damage));

        return String.format("%s attacks %s for %s damage!", attacker.getName(), defender.getName(), damage);
    }
}
