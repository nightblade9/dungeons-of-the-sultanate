package com.deengames.dungeonsofthesultanate.turnservice.core;

import com.deengames.dungeonsofthesultanate.turnservice.BaseIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TurnRepositoryIntegrationTests extends BaseIntegrationTest {

    // Subject under test
    @Autowired
    private TurnRepository turnRepository;

    @Test
    void first_ReturnsNull_IfThereAreNoRecords() {
        // Act/Assert
        Assertions.assertNull(turnRepository.first());
    }

    @Test
    void first_ReturnsArbitraryItem() {
        // Arrange
        var p1 = new PlayerTurns("player1", 123);
        var p2 = new PlayerTurns("p2", 22);
        turnRepository.save(p1);
        turnRepository.save(p2);

        // Act
        var actual = turnRepository.first();

        // Assert. Don't compare tick time, because local variable is UTC (no timezone specified, e.g. 2022-10-03T21:47:41.972311200Z), while the DB one is local time with a timezone (e.g. 2022-10-03T17:47:41.972311-04:00)
        var actualIsP1 = (actual.getUserId().equals(p1.getUserId()) && actual.getNumTurns() == p1.getNumTurns());

        var actualIsP2 = (actual.getUserId().equals(p2.getUserId()) && actual.getNumTurns() == p2.getNumTurns());

        Assertions.assertTrue(actualIsP1 || actualIsP2);
    }

    @Test
    public void getElapsedTicks_ReturnsOne_IfThereAreNoPlayerTurns() {
        // Act
        var actual = turnRepository.getElapsedTicks();
        // Assert
        Assertions.assertEquals(1, actual);
    }

    @Test
    public void getElapsedTicks_ReturnsElapsedTicksInHours_UsingArbitraryPlayerTurns() {
        // Arrange
        var expectedHours = 31;
        var p1 = new PlayerTurns("player1", 123);
        p1.setLastTurnTickUtc(p1.getLastTurnTickUtc().minusHours(expectedHours));
        var p2 = new PlayerTurns("p2", 22);
        p2.setLastTurnTickUtc(p1.getLastTurnTickUtc().minusHours(expectedHours));
        turnRepository.save(p1);
        turnRepository.save(p2);

        // Act
        var actual = turnRepository.getElapsedTicks();

        // Assert
        Assertions.assertEquals(expectedHours, actual);
    }


}
