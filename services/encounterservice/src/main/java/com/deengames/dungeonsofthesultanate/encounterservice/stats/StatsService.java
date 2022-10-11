package com.deengames.dungeonsofthesultanate.encounterservice.stats;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
public interface StatsService {

    boolean exists(ObjectId id);
    PlayerStats get(ObjectId id);
    void save(PlayerStats stats);

}