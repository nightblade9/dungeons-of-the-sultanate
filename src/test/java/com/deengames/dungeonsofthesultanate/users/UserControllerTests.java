package com.deengames.dungeonsofthesultanate.users;

import com.deengames.dungeonsofthesultanate.security.SecurityContextFetcher;
import com.deengames.dungeonsofthesultanate.security.StubToken;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTests
{
    @Autowired
    private UserController controller;

    @MockBean
    private SecurityContextFetcher securityContextFetcher;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void onlogin_InsertsUserIntoDatabase() throws Exception
    {
        // Arrange
        given(this.securityContextFetcher.getAuthentication())
            .willReturn(new StubToken("fake@fake.com"));

        // Act
        controller.onLogin();

        // Assert
        // idk, I use mockMvc :/
    }
}