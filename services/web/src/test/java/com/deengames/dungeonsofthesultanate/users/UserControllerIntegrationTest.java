package com.deengames.dungeonsofthesultanate.users;

import com.deengames.dungeonsofthesultanate.BaseIntegrationTest;
import com.deengames.dungeonsofthesultanate.web.security.SecurityContextFetcher;
import com.deengames.dungeonsofthesultanate.security.StubToken;
import com.deengames.dungeonsofthesultanate.web.security.client.ServiceToServiceClient;
import com.deengames.dungeonsofthesultanate.web.users.UserController;
import com.deengames.dungeonsofthesultanate.web.users.UserModel;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

// Integration-ish; we mock service-to-service calls.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest extends BaseIntegrationTest {

    // Subject under test
    @Autowired
    private UserController controller;

    // Mock our security context so we can control (fake) authz
    @MockBean
    private SecurityContextFetcher securityContextFetcher;

    // Mocks our service-to-service REST client so we can run without other services
    @MockBean
    private ServiceToServiceClient s2sClient;

    @Test
    public void onlogin_InsertsUserIntoDatabase() throws UsernameNotFoundException {
        // Arrange
        var expectedEmailAddress = "fake@fake.com";

        given(this.securityContextFetcher.getAuthentication())
                .willReturn(new StubToken(expectedEmailAddress));

        given(this.s2sClient.post(Mockito.anyString(), Mockito.any(), Mockito.any()))
            .willReturn("hi there");

        // Act
        controller.onLogin();

        // Assert
        var actual = userRepository.findUserByEmailAddress(expectedEmailAddress);
        assertNotNull(actual);
    }

    @Test
    public void onLogin_UpdatesExistingUserInDatabase()
    {
        // Arrange
        var expectedEmailAddress = "fake@fake.com";

        given(this.securityContextFetcher.getAuthentication())
            .willReturn(new StubToken(expectedEmailAddress));

        given(this.s2sClient.post(Mockito.anyString(), Mockito.any(), Mockito.any()))
            .willReturn("hi there");

        var expectedId = new ObjectId();
        var creationDate = new Date();
        var existingUser = new UserModel(expectedId, UserModel.calculateUserName(expectedEmailAddress), expectedEmailAddress, creationDate);
        userRepository.save(existingUser);

        // Act
        controller.onLogin();

        // Assert
        var actuals = userRepository.findAll();
        assertTrue(actuals.size() == 1);
        var actual = actuals.get(0);
        assertTrue(actual.getLastLoginUtc().after(creationDate));
    }
}