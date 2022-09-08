package com.deengames.dungeonsofthesultantate.users;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Wires up the UI/view, via the UserService
@RestController
public class UserController {
    @GetMapping("/")
    public UserModel index()
    {
//        return me or null
    }
}
