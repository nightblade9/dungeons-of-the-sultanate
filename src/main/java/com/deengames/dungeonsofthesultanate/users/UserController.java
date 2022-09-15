package com.deengames.dungeonsofthesultanate.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

//// Wires up the UI/view, via the UserService
@RestController
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    // This method invokes when the user successfully authenticates; it's configured in SecurityConfiguration,
    // via .oauth2Login().defaultSuccessUrl("/user/onLogin").
    @GetMapping("/user/onLogin")
    public RedirectView postLogin() throws UsernameNotFoundException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        // add user into database (if not there already), and update lastLogin
        var user = insertOrGetUser("herpderp");

        // update

        // Redirect. This doesn't trigger the controller, IDK why (I get a 500 error).
        return new RedirectView("/map/world");
    }

    private UserModel insertOrGetUser(String username) throws UsernameNotFoundException {
        try
        {
            return userDetailsService.loadUserByUsername(username);
        }
        catch (UsernameNotFoundException unfe)
        {
            // TODO: insert user into DB!
            try {
                return userDetailsService.loadUserByUsername(username);
            } catch (UsernameNotFoundException unfe2)
            {
                throw unfe2;
            }
        }
    }
}
