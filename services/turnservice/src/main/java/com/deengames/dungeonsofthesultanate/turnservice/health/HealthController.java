package com.deengames.dungeonsofthesultanate.turnservice.health;

import com.deengames.dungeonsofthesultanate.turnservice.core.TurnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    public static final String ROOT_URL = "health";

    // Client secret DOES have to be protected, so don't even store it in a variable
    @Autowired
    private Environment env;

    @Autowired
    private TurnRepository turnRepository;

    // This is the only health end-point that's unauthenticated, because it checks things that tell
    // us if authentication is working or broken.
    @RequestMapping(value = ROOT_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> health(Model model)
    {
        var isServiceToServiceSecretSet =
                env.getProperty("dots.service_to_service_secret") != null;

        var tickCron = env.getProperty("dots.tickCron");

        var toReturn = new HashMap<String, String>();
        toReturn.put("current_time_utc", Instant.now().toString());
        toReturn.put("profile", env.getDefaultProfiles()[0]);
        toReturn.put("s2s_auth_set", String.valueOf(isServiceToServiceSecretSet));
        toReturn.put("ticks_cron", tickCron);

        Duration onTickElapsedTime = getOnTickElapsedTime();
        toReturn.put("ticks_runtime", onTickElapsedTime.toMillis() + "ms");
        return toReturn;
    }

    private Duration getOnTickElapsedTime() {

        // This is O(n) for n players (table scan - unindexed column), so use sparingly...
        var allTicks = turnRepository.findAll();

        var minTime = OffsetDateTime.MAX;
        var maxTime = OffsetDateTime.MIN;
        for (var t : allTicks) {
            var tick = t.getLastTurnTickUtc();
            if (tick.toEpochSecond() < minTime.toEpochSecond()) {
                minTime = tick;
            }
            if (tick.toEpochSecond() > maxTime.toEpochSecond()) {
                maxTime = tick;
            }
        }

        var diff = Duration.between(minTime, maxTime);
        return diff;
    }
}