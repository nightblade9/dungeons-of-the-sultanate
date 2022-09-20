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
@Getter 
@Setter
public class UserModel implements UserDetails {

    @Id
    private ObjectId id;

    private String password;

    private String emailAddress;

    private Date lastLoginUtc;

    public UserModel(ObjectId id, String emailAddress, Date lastLoginUtc)
    {
        this.id = id;
        this.emailAddress = emailAddress;
        this.lastLoginUtc = lastLoginUtc;
    }

    @Override
    public String getUsername()
    {
        return this.emailAddress;
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
