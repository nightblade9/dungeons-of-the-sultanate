package com.deengames.dungeonsofthesultanate.encounterservice.stats;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatsRepository extends MongoRepository<PlayerStats, String> {
}
