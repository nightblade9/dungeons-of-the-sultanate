package com.deengames.dungeonsofthesultanate.services.web.maps.world;

import java.util.Arrays;

public class WorldMapLocations {
    private static final String[] LOCATIONS = { "Towering Tree Forest" };

    public static final Object[] locations =
            Arrays.stream(WorldMapLocations.LOCATIONS)
            .map(l -> new LocationData(l))
            .toArray();

}
