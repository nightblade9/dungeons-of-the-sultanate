package com.deengames.dungeonsofthesultanate.playerservice.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private StatsRepository statsRepository;

    @Override
    public void save(PlayerStats stats)
    {
        statsRepository.save(stats);
    }
}
