package com.deengames.dungeonsofthesultanate.users;

import com.deengames.dungeonsofthesultanate.security.TokenParser;
import com.deengames.dungeonsofthesultanate.security.SecurityContextFetcher;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Date;

//// Wires up the UI/view, via the UserService
@RestController
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService; // read service

    @Autowired
    private WriteUserDetailsService writeUserDetailsService;

    @Autowired
    private SecurityContextFetcher securityContextFetcher;

    // This method invokes when the user successfully authenticates; it's configured in SecurityConfiguration,
    // via .oauth2Login().defaultSuccessUrl("/user/onLogin").
    @GetMapping("/user/onLogin")
    public RedirectView onLogin() throws UsernameNotFoundException {
        var authentication = securityContextFetcher.getAuthentication();
        var userEmail = TokenParser.getUserEmailAddressFromToken(authentication);
        if (userEmail == null)
        {
            throw new UsernameNotFoundException("Couldn't get user email address from claim!");
        }

        // add user into database (if not there already), and update lastLogin
        upsertUser(userEmail);

        // Redirect. This doesn't trigger the controller, IDK why (I get a 500 error).
        return new RedirectView("/map/world");
    }

    private UserDetails upsertUser(String emailAddress) throws UsernameNotFoundException {
        var username = UserModel.calculateUserName(emailAddress);
        var user = (UserModel)userDetailsService.loadUserByUsername(username);

        // Existing user, update login time
        if (user != null) {
            user.setLastLoginUtc(new Date());
            writeUserDetailsService.saveUser(user);
            return user;
        }

        // New user, insert!
        user = new UserModel(new ObjectId(), username, emailAddress, new Date());
        writeUserDetailsService.saveUser(user);
        return user;
    }
}