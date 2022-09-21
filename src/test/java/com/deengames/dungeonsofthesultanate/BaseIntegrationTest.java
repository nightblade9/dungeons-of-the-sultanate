package com.deengames.dungeonsofthesultanate;

import com.deengames.dungeonsofthesultanate.users.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseIntegrationTest {
    // Singleton beans that we use to inspect the DB
    @Autowired
    public UserRepository userRepository;

    @BeforeEach
    @AfterEach
    public void deleteAllUsers()
    {
        userRepository.deleteAll();
    }
}
