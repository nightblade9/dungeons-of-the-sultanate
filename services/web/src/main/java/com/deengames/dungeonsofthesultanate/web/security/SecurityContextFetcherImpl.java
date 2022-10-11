package com.deengames.dungeonsofthesultanate.web.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextFetcherImpl implements SecurityContextFetcher
{
    public Authentication getAuthentication()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
