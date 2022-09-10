package com.deengames.dungeonsofthesultanate.users;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//
//// Wires up the UI/view, via the UserService
@RestController
public class UserController {
//    @GetMapping("/")
//    public UserModel index()
//    {
//        return null;
//    }

    // This method invokes when the user successfully authenticates; it's configured in SecurityConfiguration,
    // via .oauthoauth2Login().defaultSuccessUrl("/user/onLogin").
    @GetMapping("/user/onLogin")
    public void postLogin()
    {
        // TODO: redirect to home page
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("You're logged in!");
    }
}
