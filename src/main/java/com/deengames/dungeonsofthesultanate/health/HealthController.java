package com.deengames.dungeonsofthesultanate.health;

import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// TODO: shouldn't be authenticated, because we need to check if authz is broken! ...
@RestController
public class HealthController {

    public static final String ROOT_URL = "health";

    // This is the only health end-point that's unauthenticated, because it checks things that tell
    // us if authentication is working or broken.
    @RequestMapping(value = ROOT_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> health(Model model)
    {
        var toReturn = new HashMap<String, String>();
        toReturn.put("current_time_utc", Instant.now().toString());
        return toReturn;
    }

    @RequestMapping(value = ROOT_URL + "/detailed", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> detailed(Model model)
    {
        return new HashMap<String, String>();
    }
}
