package com.deengames.dungeonsofthesultanate.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class StubToken extends AbstractAuthenticationToken
{
    private String emailAddress;

    public StubToken(String emailAddress)
    {
        super(null);
        this.emailAddress = emailAddress;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return new StubOAuth2Principal(this.emailAddress);
    }
}
