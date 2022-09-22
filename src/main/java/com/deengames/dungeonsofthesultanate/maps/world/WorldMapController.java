package com.deengames.dungeonsofthesultanate.maps.world;

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
        // Used because this is where you reach on authentication.
        // TODO: use Rails' :flash for this instead.
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userEmailAddress = TokenParser.getUserEmailAddressFromToken(authentication);
        model.addAttribute("authenticatedAs", userEmailAddress);

        model.addAttribute("locations", WorldMapLocations.locations);
        return "map/world/index";
    }
}
