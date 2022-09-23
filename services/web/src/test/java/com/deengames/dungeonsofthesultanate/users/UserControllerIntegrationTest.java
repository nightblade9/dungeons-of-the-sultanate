package com.deengames.dungeonsofthesultanate.users;

import com.deengames.dungeonsofthesultanate.BaseIntegrationTest;
import com.deengames.dungeonsofthesultanate.services.web.security.SecurityContextFetcher;
import com.deengames.dungeonsofthesultanate.security.StubToken;
import com.deengames.dungeonsofthesultanate.services.web.users.UserController;
import com.deengames.dungeonsofthesultanate.services.web.users.UserModel;
import io.jsonwebtoken.lang.Assert;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest extends BaseIntegrationTest {

    // Subject under test
    @Autowired
    private UserController controller;

    // Mock our security context so we can control (fake) authz
    @MockBean
    private SecurityContextFetcher securityContextFetcher;

    @Test
    public void onlogin_InsertsUserIntoDatabase() {
        // Arrange
        var expectedEmailAddress = "fake@fake.com";

        given(this.securityContextFetcher.getAuthentication())
                .willReturn(new StubToken(expectedEmailAddress));

        // Act
        controller.onLogin();

        // Assert
        var actual = userRepository.findUserByEmailAddress(expectedEmailAddress);
        Assert.notNull(actual);
    }

    @Test
    public void onLogin_UpdatesExistingUserInDatabase()
    {
        // Arrange
        var expectedEmailAddress = "fake@fake.com";

        given(this.securityContextFetcher.getAuthentication())
                .willReturn(new StubToken(expectedEmailAddress));

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