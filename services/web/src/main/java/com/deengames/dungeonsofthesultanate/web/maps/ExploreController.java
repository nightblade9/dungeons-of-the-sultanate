package com.deengames.dungeonsofthesultanate.web.maps;

import com.deengames.dungeonsofthesultanate.web.BaseController;
import com.deengames.dungeonsofthesultanate.web.security.client.ServiceToServiceClient;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExploreController extends BaseController {

    @Autowired
    private ServiceToServiceClient s2sClient;

    @Autowired
    private Environment environment;

    @GetMapping("/explore")
    public void explore(String location) {
        if (location == null || location.isEmpty()) {
            throw new IllegalArgumentException("location");
        }

        var request = new JSONObject();
        request.put("playerId", getCurrentUser().getId().toHexString());
        request.put("locationName", location);

        var url = String.format("%s/encounter", environment.getProperty("dots.serviceToService.encounterService"));
        var battleLog = s2sClient.post(url, request, String[].class);

        System.out.println("log: " + battleLog.length);
    }
}
