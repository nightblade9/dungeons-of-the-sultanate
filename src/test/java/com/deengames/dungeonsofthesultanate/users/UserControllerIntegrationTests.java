package com.deengames.dungeonsofthesultanate.users;

import com.deengames.dungeonsofthesultanate.security.SecurityContextFetcher;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
        doReturn(new FakeToken())
                .when(securityContextFetcher).getAuthentication();

        // Act
        controller.postLogin();

        // Assert
        // idk, I use mockMvc :/
    }
}

class FakeToken extends AbstractAuthenticationToken
{
    public FakeToken() {
        super(null);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return new FakePrincipal("hi@hi.com");
    }
}

class FakePrincipal implements OAuth2AuthenticatedPrincipal {

    @Getter @Setter
    public Map<String, Object> attributes;

    @Getter @Setter
    public Collection<? extends GrantedAuthority> authorities;

    @Getter @Setter
    public String name;

    public FakePrincipal(String email)
    {
        this.attributes = new HashMap<String, Object>();
    }
}
