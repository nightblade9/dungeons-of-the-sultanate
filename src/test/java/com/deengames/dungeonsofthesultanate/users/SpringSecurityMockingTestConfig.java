package com.deengames.dungeonsofthesultanate.users;

import org.bson.types.ObjectId;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Arrays;
import java.util.Date;

@TestConfiguration
public class SpringSecurityMockingTestConfig {

    // Other bean is not @Primary; adding a name guarantees we override it, instead of getting an exception that
    // Spring already has an implementation for UserDetailsService.
    @Bean(name="stubUserDetailsBean")
    @Primary
    public UserDetailsService userDetailsService() {
        var emailAddress = "basic@mockeduser.com";
        var username = UserModel.calculateUserName(emailAddress);
        UserModel basicUser = new UserModel(new ObjectId(), username, emailAddress, new Date());
        return new InMemoryUserDetailsManager(Arrays.asList(
                basicUser
        ));
    }
}
