package com.deengames.dungeonsofthesultanate.turnservice.core;

import com.deengames.dungeonsofthesultanate.turnservice.BaseIntegrationTest;
import net.bytebuddy.description.NamedElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

public class TurnServiceIntegrationTests extends BaseIntegrationTest {

    // Subject under test
    @Autowired
    private TurnService turnService;

    @Autowired
    private TurnRepository turnRepository;

    @Test
    public void onTick_UpdatesAllPlayersWithElapsedTime()
    {
        // Arrange
        var player1 = new PlayerTurns();
        player1.setUserId("made-up ID 1");
        player1.setNumTurns(10);
        turnRepository.save(player1);

        var player2 = new PlayerTurns();
        player2.setUserId("made-up ID 2");
        player2.setNumTurns(20);
        turnRepository.save(player2);

        var elapsed = 5;

        // Act
        turnService.onTick(elapsed);

        // Assert
        var actuals = turnRepository.findAll();
        var iterator = actuals.iterator();
        var actualP1 = iterator.next();
        Assertions.assertEquals(actualP1.getNumTurns(), 10 + elapsed);
        var actualP2 = iterator.next();
        Assertions.assertEquals(actualP2.getNumTurns(), 20 + elapsed);
        Assertions.assertFalse(iterator.hasNext());
    }
}
