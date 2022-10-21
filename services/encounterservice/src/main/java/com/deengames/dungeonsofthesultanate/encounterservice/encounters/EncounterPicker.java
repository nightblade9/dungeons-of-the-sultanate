package com.deengames.dungeonsofthesultanate.encounterservice.encounters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

public class EncounterPicker {

    static class EncounterTypeWithWeight
    {
        public EncounterType encounterType;
        public float weight;

        public EncounterTypeWithWeight(EncounterType type, float weight) {
            this.encounterType = type;
            this.weight = weight;
        }
    }

    private static HashMap<Location, ArrayList<EncounterTypeWithWeight>> locationEncounters = new HashMap<>();

    public static EncounterType chooseEncounter(Location location) {
        // Initialize if not initialized correctly
        if (!locationEncounters.containsKey(location)) {
            locationEncounters.clear();

            var forestEncounters = new ArrayList<EncounterTypeWithWeight>();
            forestEncounters.add(new EncounterTypeWithWeight(EncounterType.BATTLE, 0.95f));
            forestEncounters.add(new EncounterTypeWithWeight(EncounterType.CURRENCY_DROP, 0.05f));

            locationEncounters.put(Location.TOWERING_TREE_FOREST, forestEncounters);
        }

        // Pick, now. Weighted pick, c/o https://stackoverflow.com/questions/673283/weighted-randomness-in-java
        var totalWeight = 0f;
        var ourTypes = locationEncounters.get(location);
        for (var locationData : ourTypes) {
            totalWeight += locationData.weight;
        }

        var index = 0;
        for (var r = Math.random() * totalWeight; index < ourTypes.size() - 1; ++index) {
            r -= ourTypes.get(index).weight;
            if (r <= 0) {
                break;
            }
        }
        var toReturn = ourTypes.get(index);
        return toReturn.encounterType;
    }
}
