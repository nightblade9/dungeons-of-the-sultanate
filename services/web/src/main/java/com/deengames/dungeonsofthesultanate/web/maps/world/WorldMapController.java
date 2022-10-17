package com.deengames.dungeonsofthesultanate.web.maps.world;

import com.deengames.dungeonsofthesultanate.web.BaseController;
import com.deengames.dungeonsofthesultanate.web.security.TokenParser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WorldMapController extends BaseController
{
    @GetMapping("/map/world")
    public String worldMap(Model model) {
        model.addAttribute("currentUser", this.getCurrentUser());
        model.addAttribute("locations", WorldMapLocations.locations);
        return "map/world/index";
    }
}
