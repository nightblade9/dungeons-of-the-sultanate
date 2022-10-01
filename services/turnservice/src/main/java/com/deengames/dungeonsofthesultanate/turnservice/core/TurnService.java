package com.deengames.dungeonsofthesultanate.turnservice.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

@Service
public class TurnService {

    @Autowired
    private TurnRepository repository;

    public void addNewRecord(PlayerTurns playerTurns)
    {
        repository.save(playerTurns);
    }

    public int getNumTurns(String userId) {
        var data = repository.findById(userId);
        if (data == null || data.isEmpty())
        {
            return 0;
        }

        return data.get().getNumTurns();
    }

    public void onTick(int elapsedTicks) {
        if (elapsedTicks <= 0)
        {
            // Something's wrong with our cron schedule or machine
            throw new IllegalArgumentException(String.format("onTick called with invalid elapsed ticks: %s", elapsedTicks));
        }

        var allPlayerTurns = repository.findAll();
        StreamSupport.stream(allPlayerTurns.spliterator(), false)
            .parallel().forEach(turns -> turns.setNumTurns(turns.getNumTurns() + elapsedTicks));

        repository.saveAll(allPlayerTurns);
    }
}
