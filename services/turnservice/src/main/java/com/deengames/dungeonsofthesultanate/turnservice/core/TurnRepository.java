package com.deengames.dungeonsofthesultanate.turnservice.core;

import org.springframework.data.repository.CrudRepository;

public interface TurnRepository extends CrudRepository<PlayerTurns, String> {
}
