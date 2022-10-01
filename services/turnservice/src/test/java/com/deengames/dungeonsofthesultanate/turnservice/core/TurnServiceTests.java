package com.deengames.dungeonsofthesultanate.turnservice.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class TurnServiceTests {

    @Autowired
    private TurnService turnService; // Subject under test


    @ParameterizedTest
    @ValueSource(ints = {-19, -1, 0})
    void createPlayer_AddsPlayerToService(int elapsedTicks) {
        // Act
        var exception = Assertions.assertThrows(IllegalArgumentException.class,
            () -> turnService.onTick(elapsedTicks));
        Assertions.assertTrue(exception.getMessage().contains("invalid elapsed ticks"));
    }
}
