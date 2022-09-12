package com.deengames.dungeonsofthesultanate.users;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;

//
//// Wires up the UI/view, via the UserService
@RestController
public class UserController {

    // This method invokes when the user successfully authenticates; it's configured in SecurityConfiguration,
    // via .oauthoauth2Login().defaultSuccessUrl("/user/onLogin").
    @GetMapping("/user/onLogin")
    public RedirectView postLogin()
    {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        // TODO: add user into database and update lastLogin

        // Redirect
        return new RedirectView("/");
    }
}
