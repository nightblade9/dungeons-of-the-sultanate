package com.deengames.dungeonsofthesultanate.playerservice.stats;

import org.springframework.stereotype.Service;

@Service
public interface StatsService {
    void savePlayerStats(PlayerStats stats);
}