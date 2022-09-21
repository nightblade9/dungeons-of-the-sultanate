package com.deengames.dungeonsofthesultanate;

import com.deengames.dungeonsofthesultanate.users.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
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
