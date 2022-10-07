package com.deengames.dungeonsofthesultanate.encounterservice.encounters;

import com.deengames.dungeonsofthesultanate.encounterservice.client.ServiceToServiceClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;

public class EncounterControllerTests {

    @Autowired
    private EncounterController controller;

    @MockBean
    private ServiceToServiceClient client;

    @Mock
    private Environment environment;

    // TODO: test...
    @Test
    void tryEncounter_ReturnsNoTurnsMessage_IfPlayerHasNoTurnsLeft() {

    }

    @Test
    void tryEncounter_ReturnsMessagesAndUpdatesPlayer_IfBattleProceeds() {

    }
}
