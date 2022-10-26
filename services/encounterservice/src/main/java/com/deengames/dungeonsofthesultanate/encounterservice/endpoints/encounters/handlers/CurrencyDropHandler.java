package com.deengames.dungeonsofthesultanate.encounterservice.endpoints.encounters.handlers;

import com.deengames.dungeonsofthesultanate.encounterservice.dtos.PlayerStatsDto;
import com.deengames.dungeonsofthesultanate.encounterservice.endpoints.encounters.Location;
import net.minidev.json.JSONObject;

import java.util.HashMap;

public class CurrencyDropHandler implements EncounterHandler {

    @Override
    public JSONObject handle(HashMap<String, Object> inputs) {
        var player = (PlayerStatsDto)inputs.get("player");
        var location = (Location)inputs.get("location");

        int numDinars = 0;
        switch (location) {
            case TOWERING_TREE_FOREST:
                numDinars = 123;
                break;
        }

        // TODO: player dun have currency yet, d'oh

        String[] logs = {
            "You stumble and catch yourself as you fall to the ground. As you turn, you find the object you tripped over: a buried treasure chest!",
            String.format("You scramble back and paw it out of the ground. You pry the lid open, and find %s gold dinars!", numDinars)
        };

        var imageName = "treasure";

        var result = new JSONObject();
        result.put("title", "Free loot!");
        result.put("imageName", imageName);
        result.put("logs", logs);
        return result;
    }

}
