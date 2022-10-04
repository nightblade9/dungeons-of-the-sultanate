package com.deengames.dungeonsofthesultanate.playerservice.stats;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface StatsRepository extends MongoRepository<PlayerStats, String> {
}
