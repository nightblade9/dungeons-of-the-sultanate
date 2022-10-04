package com.deengames.dungeonsofthesultanate.playerservice;

import com.deengames.dungeonsofthesultanate.playerservice.stats.StatsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class BaseIntegrationTest {

    // Singleton beans that we use to inspect the DB
    @Autowired
    public StatsRepository statsRepository;

    @BeforeEach
    @AfterEach
    public void deleteAllUsers()
    {
        statsRepository.deleteAll();
    }
}
