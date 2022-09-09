package com.deengames.dungeonsofthesultanate.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.*;

// TODO: shouldn't be authenticated, because we need to check if authz is broken! ...
@RestController
public class HealthController {

    public static final String ROOT_URL = "health";

    @Value("${spring.profiles.active}")
    private String activeProfile;

    // Does not need to be protected
    @Value("${spring.security.oauth2.client.registration.github.clientId}")
    private String oauth2CilentId;

    // Client secret DOES have to be protected, so don't even store it in a variable
    @Autowired
    private Environment env;

    // This is the only health end-point that's unauthenticated, because it checks things that tell
    // us if authentication is working or broken.
    @RequestMapping(value = ROOT_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> health(Model model)
    {
        var isClientSecretSet =
            env.getProperty("spring.security.oauth2.client.registration.github.clientId") != null;

        var toReturn = new HashMap<String, String>();
        toReturn.put("current_time_utc", Instant.now().toString());
        toReturn.put("profile", this.activeProfile);
        toReturn.put("oauth2_client_id", this.oauth2CilentId);
        toReturn.put("oauth2_client_secret_set", String.valueOf(isClientSecretSet));

        return toReturn;
    }

    // Detailed health checks; these are secured behind authentication.
    @RequestMapping(value = ROOT_URL + "/detailed", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> detailed(Model model)
    {
        return new HashMap<String, String>();
    }
}
