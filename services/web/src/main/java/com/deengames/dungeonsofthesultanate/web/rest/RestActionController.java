package com.deengames.dungeonsofthesultanate.web.rest;

import com.deengames.dungeonsofthesultanate.web.BaseController;
import com.deengames.dungeonsofthesultanate.web.security.client.ServiceToServiceClient;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RestActionController  extends BaseController {

    @Autowired
    private ServiceToServiceClient client;

    @Autowired
    private Environment environment;

    @GetMapping("/rest")
    public String rested(Model model) {
        this.addPlayerDetailsToModel(model);

        var encounterServiceUrl = environment.getProperty("dots.serviceToService.encounterService");
        var userId = this.getCurrentUser().getId().toHexString();
        var message = client.get(String.format("%s/rest?playerId=%s", encounterServiceUrl, userId), String.class);
        model.addAttribute("restMessage", message);
        return "encounters/rested";
    }
}
