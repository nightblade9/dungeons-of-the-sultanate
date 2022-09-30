package com.deengames.dungeonsofthesultanate.services.web.maps.toweringtreeforest;

import com.deengames.dungeonsofthesultanate.services.web.BaseController;
import com.deengames.dungeonsofthesultanate.services.web.maps.BaseMapController;
import com.deengames.dungeonsofthesultanate.services.web.maps.world.WorldMapLocations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ToweringTreeForestController extends BaseMapController {

    @GetMapping("/map/towering-tree-forest")
    public String worldMap(Model model) {
        model.addAttribute("location", WorldMapLocations.locations[0]);
        model.addAttribute("turnsLeft", this.getNumTurns());
        return "map/toweringtreeforest/index";
    }
}
