package com.deengames.dungeonsofthesultanate.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public class CurrentUser {

    // Gets the current user from the OAuth2 authentication security context.
    // Doesn't work on pages that don't require authentication (see SecurityConfiguration.java),
    // because there's no security context for those routes.
    public static String getCurrentUser() throws Exception
    {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
        {
            return null;
        }

        var token = (OAuth2AuthenticationToken)authentication;
        if (token == null)
        {
            // Not a valid OAUth2 token ...
            return null;
        }
        // "login" is what GitHub OAuth2 gives us, "email" is what Google OAuth2 gives us
        String userName = getUserNameFromToken(token);
        return userName;
    }

    private static String getUserNameFromToken(OAuth2AuthenticationToken token)
    {
        // TODO: strategy pattern to get rid of switch/case based on issuer type
        var attributes = token.getPrincipal().getAttributes();
        
        if (attributes.containsKey("login"))
        {
            // GitHub
            return attributes.get("login").toString();
        }
        else if (attributes.containsKey("name"))
        {
            // Google
            return attributes.get("name").toString();
        }
        else
        {
            throw new SecurityException("Not sure how to get user name from token!");
        }
    }
}
