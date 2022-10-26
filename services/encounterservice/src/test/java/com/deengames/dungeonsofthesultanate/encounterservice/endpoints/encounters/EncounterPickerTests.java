package com.deengames.dungeonsofthesultanate.encounterservice.endpoints.encounters;

import com.deengames.dungeonsofthesultanate.encounterservice.endpoints.encounters.EncounterPicker;
import com.deengames.dungeonsofthesultanate.encounterservice.endpoints.encounters.EncounterType;
import com.deengames.dungeonsofthesultanate.encounterservice.endpoints.encounters.Location;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class EncounterPickerTests {

    @Test
    public void chooseEncounter_RandomlyChoosesWeightedEncounters() {
        var results = new ArrayList<EncounterType>();

        // Act
        while (results.size() < 100) {
            var encounter = EncounterPicker.chooseEncounter(Location.TOWERING_TREE_FOREST);
            results.add(encounter);
        }

        // Assert
        var numBattles = results.stream().filter(e -> e == EncounterType.BATTLE).count();
        var numCurrencyDrops = results.stream().filter(e -> e == EncounterType.CURRENCY_DROP).count();

        // Conservative ranges make for stable tests 👌
        Assertions.assertThat(numBattles >= 90);
        Assertions.assertThat(numCurrencyDrops <= 10);
    }

}
