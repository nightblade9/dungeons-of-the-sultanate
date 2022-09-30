package com.deengames.dungeonsofthesultanate.turnservice.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
