package com.deengames.dungeonsofthesultanate.users;

import com.deengames.dungeonsofthesultanate.security.SecurityContextFetcher;
import com.deengames.dungeonsofthesultanate.security.StubToken;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;

import java.util.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner.class)
public class UserControllerIntegrationTests
{
    @InjectMocks
    private UserController controller;

    @Mock
    private SecurityContextFetcher securityContextFetcher;

    @Test
    public void onlogin_InsertsUserIntoDatabase() throws Exception
    {
        // Arrange
        doReturn(new StubToken("fake@fake.com"))
                .when(securityContextFetcher).getAuthentication();

        // Act
        controller.postLogin();

        // Assert
        // idk, I use mockMvc :/
    }
}