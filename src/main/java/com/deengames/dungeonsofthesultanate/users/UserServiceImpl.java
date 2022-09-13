package com.deengames.dungeonsofthesultanate.users;

import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl {
    @Autowired
    private UserRepository userRepository;

    // Use the repo for get/put/etc.
    public void updateUserLastLogin(String userName)
    {

    }
}
