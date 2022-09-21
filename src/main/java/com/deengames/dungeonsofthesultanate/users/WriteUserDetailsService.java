package com.deengames.dungeonsofthesultanate.users;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface WriteUserDetailsService  {
    void saveUser(UserDetails user);
}