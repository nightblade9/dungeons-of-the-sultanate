package com.deengames.dungeonsofthesultanate.web.rest;

import com.deengames.dungeonsofthesultanate.dtos.PlayerStatsDto;
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
public class RestActionController extends BaseController
{
    @Autowired
    private ServiceToServiceClient client;

    @Autowired
    private Environment environment;

    @GetMapping("/rest")
    public RedirectView rest() {
        var user = this.getCurrentUser();
        var turnServiceUrl = environment.getProperty("dots.serviceToService.turnService");
        var consumedTurn = client.patch(String.format("%s/turns", turnServiceUrl), user.getId().toString(), Boolean.class);

        if (consumedTurn) {
            // Grab the player
            var playerServiceUrl = environment.getProperty("dots.serviceToService.playerService");
            var getPlayerUrl = String.format("%s/stats/%s", playerServiceUrl, user.getId().toString());

            var player = client.get(getPlayerUrl, PlayerStatsDto.class);
            player.setCurrentHealth(player.getMaxHealth());
            player.setCurrentEnergy(player.getMaxEnergy());

            var updatePlayerUrl = String.format("%s/stats/%s", playerServiceUrl, user.getId());
            client.put(updatePlayerUrl, player, String.class);
        }

        return new RedirectView(String.format("/rest/rested?isRested=%s", consumedTurn));
    }

    @GetMapping("/rest/rested")
    public String rested(@RequestParam boolean isRested, Model model) {
        this.addPlayerDetailsToModel(model);

        var restMessage = isRested ?
                "You take a quick nap and wake up feeling refreshed." :
                "You don't have any turns left!";

        model.addAttribute("restMessage", restMessage);
        return "encounters/rested";
    }
}
