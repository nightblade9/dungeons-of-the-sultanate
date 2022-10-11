package com.deengames.dungeonsofthesultanate.web.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.*;

@RestController
public class HealthController {

    public static final String ROOT_URL = "health";

    // Client secret DOES have to be protected, so don't even store it in a variable
    @Autowired
    private Environment env;

    // This is the only health end-point that's unauthenticated, because it checks things that tell
    // us if authentication is working or broken.
    @RequestMapping(value = ROOT_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> health(Model model)
    {
        var isClientSecretSet =
            env.getProperty("spring.security.oauth2.client.registration.github.clientSecret") != null;

        var isServiceToServiceSecretSet =
            env.getProperty("dots.serviceToService.secret") != null;

        var toReturn = new HashMap<String, String>();
        toReturn.put("current_time_utc", Instant.now().toString());
        toReturn.put("profile", env.getDefaultProfiles()[0]);
        toReturn.put("oauth2_client_id", env.getProperty("spring.security.oauth2.client.registration.github.clientId"));
        toReturn.put("oauth2_client_secret_set", String.valueOf(isClientSecretSet));
        toReturn.put("s2s_auth_set", String.valueOf(isServiceToServiceSecretSet));

        return toReturn;
    }
}
