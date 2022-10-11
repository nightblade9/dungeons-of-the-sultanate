package com.deengames.dungeonsofthesultanate.encounterservice.stats;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private StatsRepository statsRepository;

    @Override
    public PlayerStats get(ObjectId id) {
        return statsRepository.findById(id.toHexString()).get();
    }

    @Override
    public boolean exists(ObjectId id) {
        return statsRepository.existsById(id.toHexString());
    }

    @Override
    public void save(PlayerStats stats)
    {
        statsRepository.save(stats);
    }
}
