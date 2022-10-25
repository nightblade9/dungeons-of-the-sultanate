package com.deengames.dungeonsofthesultanate.web.home;

import com.deengames.dungeonsofthesultanate.web.BaseController;
import com.deengames.dungeonsofthesultanate.web.security.client.ServiceToServiceClient;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class GlobalActionsController extends BaseController
{
    @Autowired
    private ServiceToServiceClient client;

    @Autowired
    private Environment environment;

    @GetMapping("/global/rest")
    public String rest() {
        var user = this.getCurrentUser();
        var turnServiceUrl = environment.getProperty("dots.serviceToService.turnService");
        var consumedTurn = client.patch(turnServiceUrl, user.getId().toString(), Boolean.class);

        if (consumedTurn) {
            // Grab the player
            var playerServiceUrl = environment.getProperty("dots.serviceToService.playerService");
            var getPlayerUrl = String.format("%s/stats/%s", playerServiceUrl, user.getId().toString());

            // TODO: use PlayerStats DTO instead
            var player = client.get(getPlayerUrl, JSONObject.class);
            player.put("currentHealth", player.get("maxHealth"));

            var updatePlayerUrl = String.format("%s/stats/%s", playerServiceUrl, user.getId());
            client.put(updatePlayerUrl, player, String.class);
        }

        return String.format("/global/rested?isRested=%s", consumedTurn);
    }

    @GetMapping("/global/rested")
    public String rested(@RequestParam boolean isRested, Model model) {
        var restMessage = isRested ?
                "You take a quick nap and wake up feeling refreshed." :
                "You don't have any turns left!";
        
        model.addAttribute("restMessage", restMessage);
        return "global/rested";
    }
}
