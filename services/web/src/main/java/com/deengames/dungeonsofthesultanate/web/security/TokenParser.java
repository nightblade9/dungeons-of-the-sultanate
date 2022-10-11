package com.deengames.dungeonsofthesultanate.web.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

public class TokenParser {

    // Gets the current user from the OAuth2 authentication security context.
    // Doesn't work on pages that don't require authentication (see SecurityConfiguration.java),
    // because there's no security context for those routes.
    public static String getUserEmailAddressFromToken(Authentication authentication)
    {
        if (authentication instanceof AnonymousAuthenticationToken)
        {
            return null;
        }

        var token = (AbstractAuthenticationToken)authentication;
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
        if (principal instanceof OAuth2AuthenticatedPrincipal) {
            // GitHub and Google; Google is DefaultOidcUser, which is a subclass or something?
            return ((OAuth2AuthenticatedPrincipal)principal).getAttribute("email").toString();
        }

        // Token tampered with, username claim not present, OAuth provider not supported, etc.
        return null;
    }
}
