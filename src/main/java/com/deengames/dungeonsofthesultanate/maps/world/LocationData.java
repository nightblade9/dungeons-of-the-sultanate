package com.deengames.dungeonsofthesultanate.maps.world;

public class LocationData {

    public String name;
    public final String slug;

    public LocationData(String name)
    {
        this.name = name;
        this.slug = name.toLowerCase().replace(' ', '-');
    }
}