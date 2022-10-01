package com.deengames.dungeonsofthesultanate.turnservice.core;

import com.deengames.dungeonsofthesultanate.turnservice.BaseIntegrationTest;
import net.bytebuddy.description.NamedElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

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
        var distantPast = OffsetDateTime.of(2001, 12, 31, 17, 56, 0, 0, ZoneOffset.UTC);

        var player1 = new PlayerTurns();
        player1.setUserId("made-up ID 1");
        player1.setNumTurns(10);
        player1.setLastTurnTickUtc(distantPast);
        turnRepository.save(player1);

        var player2 = new PlayerTurns();
        player2.setUserId("made-up ID 2");
        player2.setNumTurns(20);
        player2.setLastTurnTickUtc(distantPast);
        turnRepository.save(player2);

        var elapsed = 5;

        // Act
        turnService.onTick(elapsed);

        // Assert
        var actuals = turnRepository.findAll();
        var iterator = actuals.iterator();

        var actualP1 = iterator.next();
        Assertions.assertEquals(actualP1.getNumTurns(), 10 + elapsed);
        Assertions.assertNotEquals(actualP1.getLastTurnTickUtc().toEpochSecond(), distantPast.toEpochSecond());

        var actualP2 = iterator.next();
        Assertions.assertEquals(actualP2.getNumTurns(), 20 + elapsed);
        Assertions.assertNotEquals(actualP2.getLastTurnTickUtc().toEpochSecond(), distantPast.toEpochSecond());

        Assertions.assertFalse(iterator.hasNext());
    }
}
