package com.deengames.dungeonsofthesultanate.turnservice;

import com.deengames.dungeonsofthesultanate.turnservice.core.TurnRepository;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest // switches to embedded DB
@AutoConfigureEmbeddedDatabase
public class BaseIntegrationTest {

    // Singleton beans that we use to inspect the DB
    @Autowired
    private TurnRepository turnRepository;

    @BeforeEach
    @AfterEach
    public void deleteAllUsers()
    {
        turnRepository.deleteAll();
    }
}
