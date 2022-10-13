package com.deengames.dungeonsofthesultanate.web.maps.world;

import javax.xml.stream.Location;
import java.util.HashMap;

public class LocationData {

    public String name;
    public final String slug;

    private static HashMap<String, LocationData> slugToLocation = new HashMap<>();

    public LocationData(String name)
    {
        this.name = name;
        this.slug = name.toLowerCase().replace(' ', '-');
    }

    public static LocationData findBySlug(String locationSlug) {
        // Cache out of date? Clear/refresh.
        if (!LocationData.slugToLocation.containsKey(locationSlug)) {
            slugToLocation.clear();
            var all = WorldMapLocations.locations;
            for (var location : all) {
                var data = (LocationData)location;
                LocationData.slugToLocation.put(data.slug, data);
            }
        }

        return LocationData.slugToLocation.get(locationSlug);
    }
}