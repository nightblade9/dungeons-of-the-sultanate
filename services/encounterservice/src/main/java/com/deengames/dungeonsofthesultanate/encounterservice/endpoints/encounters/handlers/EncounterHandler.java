package com.deengames.dungeonsofthesultanate.encounterservice.endpoints.encounters.handlers;

import com.deengames.dungeonsofthesultanate.encounterservice.dtos.PlayerStatsDto;
import net.minidev.json.JSONObject;

import java.util.HashMap;

public interface EncounterHandler {
    JSONObject handle(HashMap<String, Object> inputs) throws Exception;
}
