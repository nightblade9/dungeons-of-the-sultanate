package com.deengames.dungeonsofthesultanate.web.security;

import org.springframework.security.core.Authentication;

public interface SecurityContextFetcher
{
    Authentication getAuthentication();
}
