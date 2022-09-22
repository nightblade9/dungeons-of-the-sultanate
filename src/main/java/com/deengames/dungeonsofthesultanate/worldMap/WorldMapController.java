package com.deengames.dungeonsofthesultanate.worldmap;

import com.deengames.dungeonsofthesultanate.security.TokenParser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WorldMapController
{
    @GetMapping("/map/world")
    public String worldMap(Model model) throws Exception
    {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userEmailAddress = TokenParser.getUserEmailAddressFromToken(authentication);
        model.addAttribute("authenticatedAs", userEmailAddress);
        return "map/world/index";
    }
}
