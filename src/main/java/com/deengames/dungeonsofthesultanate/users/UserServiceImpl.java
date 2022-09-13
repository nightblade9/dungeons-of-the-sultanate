package com.deengames.dungeonsofthesultanate.users;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    public void updateUserLastLogin(String userName)
    {
        var user = userRepository.findUserByUserName(userName);
        user.setLastLoginUtc(new Date());
        userRepository.save(user);
    }
}
