package com.deengames.dungeonsofthesultanate.maps;

import com.deengames.dungeonsofthesultanate.security.TokenParser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WorldMapController
{
    @GetMapping("/map/world")
    public String worldMap(Model model) {
        // Used because this is where you reach on authentication
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userEmailAddress = TokenParser.getUserEmailAddressFromToken(authentication);
        model.addAttribute("authenticatedAs", userEmailAddress);

        var locations = Arrays.stream(WorldMapLocations.LOCATIONS)
            .map(l -> new LocationData(l))
            .toArray();

        model.addAttribute("locations", locations);
        return "map/world/index";
    }

    class LocationData {

        public String name;
        public final String slug;

        public LocationData(String name)
        {
            this.name = name;
            this.slug = name.toLowerCase().replace(' ', '-');
        }
    }
}
