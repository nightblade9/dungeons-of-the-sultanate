package com.deengames.dungeonsofthesultanate.health;

import com.deengames.dungeonsofthesultanate.services.web.health.HealthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HealthControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    public void getIndexSucceedsWithoutAuthentication() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.get(String.format("/%s", HealthController.ROOT_URL))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("current_time_utc")));
    }

    @Test
    public void getDetailedRedirectsToAuthPage() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.get(String.format("/%s/detailed", HealthController.ROOT_URL))
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(header().stringValues("Location", "http://localhost/login"))
        .andExpect(status().is3xxRedirection());
    }
}
