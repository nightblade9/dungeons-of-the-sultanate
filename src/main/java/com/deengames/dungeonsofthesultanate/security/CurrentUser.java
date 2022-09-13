package com.deengames.dungeonsofthesultanate.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public class CurrentUser {

    // Gets the current user from the OAuth2 authentication security context.
    // Doesn't work on pages that don't require authentication (see SecurityConfiguration.java),
    // because there's no security context for those routes.
    public static String getCurrentUser()
    {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
        {
            return null;
        }

        var token = (OAuth2AuthenticationToken)authentication;
        if (token == null)
        {
            // Not a GitHub? OAUth2 token ...
            return null;
        }
        // "login" is what GitHub OAuth2 gives us
        var userName = token.getPrincipal().getAttributes().get("login").toString();
        return userName;
    }
}
