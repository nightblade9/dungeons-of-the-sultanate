package com.deengames.dungeonsofthesultanate.maps;

import com.deengames.dungeonsofthesultanate.security.TokenParser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WorldMapController
{
    @GetMapping("/map/world")
    public String worldMap(Model model) {
        // Used because this is where you reach on authentication
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userEmailAddress = TokenParser.getUserEmailAddressFromToken(authentication);
        model.addAttribute("authenticatedAs", userEmailAddress);

        model.addAttribute("locations", WorldMapLocations.LOCATIONS);
        return "map/world/index";
    }
}
