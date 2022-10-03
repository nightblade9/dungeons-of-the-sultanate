package com.deengames.dungeonsofthesultanate.turnservice.core;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface TurnRepository extends CrudRepository<PlayerTurns, String> {

    @Query("select t from PlayerTurns t")
    List<PlayerTurns> getPaged(Pageable page);
    default PlayerTurns first() {
        var result = getPaged(PageRequest.of(0, 1));
        if (result.size() == 0)
        {
            return null;
        }

        return result.get(0);
    }

    // A travesty that belongs here ...
    default int getElapsedTicks() {
        // Picks an arbitrary (first) element. If turn time takes more than an hour/tick,
        // this can return the wrong value. HealthCheck reports that, so check it out first.
        var arbitraryTurn = this.first();
        if (arbitraryTurn == null) {
            return 1; // meh, no players or something. S'all good.
        }

        var previousTick = arbitraryTurn.getLastTurnTickUtc();
        var now = PlayerTurns.utcNow();
        var elapsed = Duration.between(previousTick, now);
        // I really don't expect a number greater than 2^32 here... hence (int)
        var toReturn = (int)elapsed.toSeconds() / 3600;
        return toReturn;
    }
}
