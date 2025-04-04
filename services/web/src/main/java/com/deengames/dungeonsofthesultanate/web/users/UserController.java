package com.deengames.dungeonsofthesultanate.web.users;

import com.deengames.dungeonsofthesultanate.web.BaseController;
import com.deengames.dungeonsofthesultanate.web.security.client.ServiceToServiceClient;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;

//// Wires up the UI/view, via the UserService
@RestController
public class UserController extends BaseController {

    @Autowired
    private WriteUserDetailsService writeUserDetailsService;

    @Autowired
    private ServiceToServiceClient s2sClient;

    @Autowired
    private Environment environment;

    // This method invokes when the user successfully authenticates; it's configured in SecurityConfiguration,
    // via .oauth2Login().defaultSuccessUrl("/user/onLogin").
    @GetMapping(value = "/user/onLogin")
    public RedirectView onLogin() throws UsernameNotFoundException {
        // add new-user data into database (if not there already), and update lastLogin
        var user = (UserModel)upsertUser();

        // Redirect. This doesn't trigger the controller, IDK why (I get a 500 error).
        return new RedirectView("/map/world");
    }

    private UserDetails upsertUser() throws UsernameNotFoundException {
        var user = this.getCurrentUser();

        // Existing user, update login time
        if (user != null) {
            user.setLastLoginUtc(new Date());
            writeUserDetailsService.saveUser(user);
            return user;
        }

        // New user, insert!
        var emailAddress = this.getUserEmailFromToken();
        var username = UserModel.calculateUserName(emailAddress);
        user = new UserModel(new ObjectId(), username, emailAddress, new Date());
        writeUserDetailsService.saveUser(user);

        // AND! Notify all the other services. (Yes, this is orchestration ...)
        initializeUser(user);

        return user;
    }

    private void initializeUser(UserModel user)
    {
        var userId = user.getId().toString();
        var turnServiceUrl = environment.getProperty("dots.serviceToService.turnService");
        s2sClient.post(String.format("%s/turns", turnServiceUrl), userId, String.class);

        var playerServiceUrl = environment.getProperty("dots.serviceToService.playerService");
        s2sClient.post(String.format("%s/stats", playerServiceUrl), userId, String.class);
    }
}