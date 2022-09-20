package com.deengames.dungeonsofthesultanate.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class CurrentUser {

    // Gets the current user from the OAuth2 authentication security context.
    // Doesn't work on pages that don't require authentication (see SecurityConfiguration.java),
    // because there's no security context for those routes.
    public static String getUserEmailAddressFromToken(Authentication authentication)
    {
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

        return getUserEmailAddress(authentication);
    }

    private static String getUserEmailAddress(Authentication authentication) throws UsernameNotFoundException
    {
        var principal = authentication.getPrincipal();
        if (principal instanceof DefaultOAuth2User) {
            // GitHub and Google; Google is DefaultOidcUser, which is a subclass or something?
            return ((DefaultOAuth2User)principal).getAttribute("email").toString();
        }

        // Token tampered with, username claim not present, OAuth provider not supported, etc.
        return null;
    }
}
