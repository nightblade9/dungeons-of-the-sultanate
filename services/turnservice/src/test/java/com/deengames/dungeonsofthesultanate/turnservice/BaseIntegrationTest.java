package com.deengames.dungeonsofthesultanate.turnservice;

import com.deengames.dungeonsofthesultanate.turnservice.core.TurnRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@DataJpaTest // switches to embedded DB
public class BaseIntegrationTest {

    // Singleton beans that we use to inspect the DB
    @Autowired
    public TurnRepository turnRepository;

    @BeforeEach
    @AfterEach
    public void deleteAllUsers()
    {
        turnRepository.deleteAll();
    }
}
