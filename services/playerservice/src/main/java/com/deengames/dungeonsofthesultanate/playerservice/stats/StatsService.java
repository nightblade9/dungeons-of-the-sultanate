package com.deengames.dungeonsofthesultanate.playerservice.stats;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface StatsService {

    boolean exists(ObjectId id);
    Optional<PlayerStats> get(ObjectId id);
    void save(PlayerStats stats);

}