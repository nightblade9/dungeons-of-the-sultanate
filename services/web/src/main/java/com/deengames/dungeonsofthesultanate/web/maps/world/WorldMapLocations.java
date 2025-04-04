package com.deengames.dungeonsofthesultanate.web.maps.world;

import java.util.Arrays;

public class WorldMapLocations {
    private static final String[] LOCATIONS = { "Towering Tree Forest" };

    public static final Object[] locations =
            Arrays.stream(WorldMapLocations.LOCATIONS)
            .map(LocationData::new)
            .toArray();

}
