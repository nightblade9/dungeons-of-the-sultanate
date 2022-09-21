package com.deengames.dungeonsofthesultanate.users;

import com.deengames.dungeonsofthesultanate.security.CurrentUser;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;

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
        var userEmail = CurrentUser.getUserEmailAddressFromToken(authentication);
        if (userEmail == null)
        {
            throw new UsernameNotFoundException("Couldn't get user email address from claim!");
        }

        // add user into database (if not there already), and update lastLogin
        var user = insertOrGetUser(userEmail);

        // Redirect. This doesn't trigger the controller, IDK why (I get a 500 error).
        return new RedirectView("/map/world");
    }

    private UserModel insertOrGetUser(String userEmailAddress) throws UsernameNotFoundException {
        var user = userDetailsService.loadUserByUsername(userEmailAddress);

        // Existing user, update login time
        if (user != null) {
            user.setLastLoginUtc(new Date());
            userDetailsService.saveUser(user);
            return user;
        }

        // New user, insert!
        user = new UserModel(new ObjectId(), null, userEmailAddress, new Date());
        userDetailsService.saveUser(user);
        return user;
    }
}