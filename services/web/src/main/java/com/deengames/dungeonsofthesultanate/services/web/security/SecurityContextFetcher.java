package com.deengames.dungeonsofthesultanate.services.web.security;

import org.springframework.security.core.Authentication;

public interface SecurityContextFetcher
{
    Authentication getAuthentication();
}
