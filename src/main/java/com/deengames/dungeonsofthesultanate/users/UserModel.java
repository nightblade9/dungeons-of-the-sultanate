package com.deengames.dungeonsofthesultanate.users;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Role;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Document
public class UserModel implements UserDetails {

    @MongoId
    @Getter @Setter
    private ObjectId id;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String password;

    @Getter @Setter
    private String emailAddress;

    @Getter @Setter
    private Date lastLoginUtc;

    public UserModel(ObjectId id, String username, String emailAddress, Date lastLoginUtc)
    {
        this.id = id;
        this.username = username;
        this.emailAddress = emailAddress;
        this.lastLoginUtc = lastLoginUtc;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
