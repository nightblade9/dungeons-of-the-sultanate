package com.deengames.dungeonsofthesultanate.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A stub OAuth2 principal with an injectable/settable user email claim (and potentially other claims).
 */
class StubOAuth2Principal implements OAuth2AuthenticatedPrincipal {

    @Getter
    @Setter
    public Map<String, Object> attributes;

    @Getter @Setter
    public Collection<? extends GrantedAuthority> authorities;

    @Getter @Setter
    public String name;

    public StubOAuth2Principal(String email)
    {
        this.attributes = new HashMap<String, Object>();
        this.attributes.put("email", email);
    }
}
