package com.deengames.dungeonsofthesultanate.turnservice.core;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class PlayerTurns {

    private final int NewPlayerNumTurns = 25;

    public PlayerTurns()
    {
        this.numTurns = NewPlayerNumTurns;
    }

    // In truth: a MongoDB BSON ID
    @Id
    @Getter @Setter
    private String playerId;

    @Getter @Setter
    private int numTurns;

    @Getter @Setter
    private Instant lastTurnTickUtc;
}
