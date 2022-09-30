package com.deengames.dungeonsofthesultanate.turnservice;

import com.deengames.dungeonsofthesultanate.turnservice.core.TurnRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase // switches to embedded DB
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
