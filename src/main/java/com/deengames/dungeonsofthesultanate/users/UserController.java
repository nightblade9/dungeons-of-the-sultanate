package com.deengames.dungeonsofthesultanate.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

//// Wires up the UI/view, via the UserService
@RestController
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    // This method invokes when the user successfully authenticates; it's configured in SecurityConfiguration,
    // via .oauth2Login().defaultSuccessUrl("/user/onLogin").
    @GetMapping("/user/onLogin")
    public RedirectView postLogin() throws UsernameNotFoundException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userLogin = getLogin(authentication);
        // add user into database (if not there already), and update lastLogin
        var user = insertOrGetUser(userLogin);

        // update

        // Redirect. This doesn't trigger the controller, IDK why (I get a 500 error).
        return new RedirectView("/map/world");
    }

    // TODO: doesn't belong here
    // TODO: refactor to use strategy pattern to encapsulate OAuth-provider specifics
    private String getLogin(Authentication authentication) throws UsernameNotFoundException
    {
        if (authentication instanceof OAuth2AuthenticationToken)
        {
            var principal = (DefaultOAuth2User)(authentication.getPrincipal());
            return principal.getAttribute("login").toString();
        }
        else if (authentication.getPrincipal() instanceof DefaultOAuth2User)
        {
            var principal = (DefaultOidcUser)(authentication.getPrincipal());
            return principal.getAttribute("name");
        }

        throw new UsernameNotFoundException("OAuth2 provider not supported");
    }

    private UserModel insertOrGetUser(String username) throws UsernameNotFoundException {
        try
        {
            return userDetailsService.loadUserByUsername(username);
        }
        catch (UsernameNotFoundException unfe)
        {
            // TODO: insert user into DB!
            try {
                return userDetailsService.loadUserByUsername(username);
            } catch (UsernameNotFoundException unfe2)
            {
                throw unfe2;
            }
        }
    }
}
