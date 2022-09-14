package com.deengames.dungeonsofthesultanate.worldMap;

import com.deengames.dungeonsofthesultanate.security.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WorldMapController
{
    @GetMapping("/map/world")
    public String worldMap(Model model) throws Exception
    {
        var currentUser = CurrentUser.getCurrentUser();
        model.addAttribute("authenticatedAs", currentUser);
        return "map/world/index";
    }
}
