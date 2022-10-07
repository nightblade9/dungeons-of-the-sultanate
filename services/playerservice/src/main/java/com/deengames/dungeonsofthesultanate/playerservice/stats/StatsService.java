package com.deengames.dungeonsofthesultanate.playerservice.stats;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface StatsService {

    boolean exists(ObjectId id);
    PlayerStats get(ObjectId id);
    void save(PlayerStats stats);

}