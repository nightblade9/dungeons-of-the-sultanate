package com.deengames.dungeonsofthesultanate.web.users;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Document @Getter @Setter
public class UserModel implements UserDetails {

    @Id
    private ObjectId id;

    private String username;

    private String password;

    private String emailAddress;

    private Date lastLoginUtc;

    // WARNING: allowing users to change their username, will break authentication! On login, we only have a token
    // with the email address; we look up the user by username, since that's what the base Spring UserService wants.
    public static String calculateUserName(String emailAddress)
    {
        var username = emailAddress.substring(0, emailAddress.indexOf('@'));
        return username.toLowerCase();
    }

    public UserModel(ObjectId id, String username, String emailAddress, Date lastLoginUtc)
    {
        this.id = id;
        this.emailAddress = emailAddress;
        this.lastLoginUtc = lastLoginUtc;
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
