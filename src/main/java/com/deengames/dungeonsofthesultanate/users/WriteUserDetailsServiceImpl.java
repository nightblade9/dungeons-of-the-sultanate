package com.deengames.dungeonsofthesultanate.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class WriteUserDetailsServiceImpl implements WriteUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveUser(UserDetails user)
    {
        UserModel userModel = (UserModel)user;
        userRepository.save(userModel);
    }
}
