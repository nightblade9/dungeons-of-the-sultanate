package com.deengames.dungeonsofthesultanate.services.web.maps.toweringtreeforest;

import com.deengames.dungeonsofthesultanate.services.web.maps.world.WorldMapLocations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Controller
public class ToweringTreeForestController {

    @GetMapping("/map/towering-tree-forest")
    public String worldMap(Model model) {
        model.addAttribute("location", WorldMapLocations.locations[0]);
        return "map/toweringtreeforest/index";
    }
}
