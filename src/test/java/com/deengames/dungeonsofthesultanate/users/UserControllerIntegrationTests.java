package com.deengames.dungeonsofthesultanate.users;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = SpringSecurityMockingTestConfig.class
)

@AutoConfigureMockMvc
public class UserControllerIntegrationTests
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("basic@mockeduser.com")
    public void onlogin_InsertsUserIntoDatabase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/onLogin")
            .accept(MediaType.ALL))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("basic@mockedmvcuser.com")));
    }
}