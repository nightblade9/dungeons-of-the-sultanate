package com.deengames.dungeonsofthesultanate.security;

import org.springframework.security.core.Authentication;

public interface SecurityContextFetcher
{
    Authentication getAuthentication();
}
