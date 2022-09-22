package com.deengames.dungeonsofthesultanate.maps;

import java.util.Arrays;

public class WorldMapLocations {
    private static final String[] LOCATIONS = { "Towering Tree Forest" };

    public static final Object[] locations =
            Arrays.stream(WorldMapLocations.LOCATIONS)
            .map(l -> new LocationData(l))
            .toArray();

    public static class LocationData {

        public String name;
        public final String slug;

        public LocationData(String name)
        {
            this.name = name;
            this.slug = name.toLowerCase().replace(' ', '-');
        }
    }
}
