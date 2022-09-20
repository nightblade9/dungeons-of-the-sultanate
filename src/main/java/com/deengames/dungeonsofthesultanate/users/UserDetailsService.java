package com.deengames.dungeonsofthesultanate.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserModel loadUserByUsername(String userEmailAddress) throws UsernameNotFoundException
    {
        UserModel user = userRepository.findUserByEmailAddress(userEmailAddress);
        return user;
    }

    public void saveUser(UserModel user)
    {
        userRepository.save(user);
    }
}