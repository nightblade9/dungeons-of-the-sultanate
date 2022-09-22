package com.deengames.dungeonsofthesultanate.maps.toweringtreeforest;

import com.deengames.dungeonsofthesultanate.maps.world.WorldMapLocations;
import com.deengames.dungeonsofthesultanate.security.TokenParser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ToweringTreeForestController
{
    @GetMapping("/map/towering-tree-forest")
    public String worldMap(Model model) {
        return "map/toweringtreeforest/index";
    }
}
