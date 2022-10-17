package com.deengames.dungeonsofthesultanate.web.maps.toweringtreeforest;

import com.deengames.dungeonsofthesultanate.web.maps.BaseMapController;
import com.deengames.dungeonsofthesultanate.web.maps.world.WorldMapLocations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ToweringTreeForestController extends BaseMapController {

    @GetMapping(value = "/map/towering-tree-forest")
    public String worldMap(Model model) {
        this.addPlayerDetailsToModel(model);
        model.addAttribute("location", WorldMapLocations.locations[0]);
        model.addAttribute("turnsLeft", this.getNumTurns());
        return "map/toweringtreeforest/index";
    }
}
