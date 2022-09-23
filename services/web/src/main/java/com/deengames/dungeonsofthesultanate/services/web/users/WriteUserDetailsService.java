package com.deengames.dungeonsofthesultanate.services.web.users;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface WriteUserDetailsService  {
    void saveUser(UserDetails user);
}