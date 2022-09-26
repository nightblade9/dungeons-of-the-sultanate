package com.deengames.dungeonsofthesultanate.turnservice.core;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Clock;
import java.time.OffsetDateTime;

@Entity
public class PlayerTurns {

    static final int newPlayerNumTurns = 25;

    public PlayerTurns()
    {
        this.numTurns = newPlayerNumTurns;
        var utcNow = OffsetDateTime.now(Clock.systemUTC().getZone());
        this.lastTurnTickUtc = utcNow;
    }

    // In truth: a MongoDB BSON ID
    @Id
    @Getter @Setter
    private String userId;

    @Getter @Setter
    private int numTurns;

    @Getter @Setter
    private OffsetDateTime lastTurnTickUtc;
}
