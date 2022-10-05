package com.deengames.dungeonsofthesultanate.services.web.users;

import com.deengames.dungeonsofthesultanate.services.web.BaseController;
import com.deengames.dungeonsofthesultanate.services.web.security.client.ServiceToServiceClient;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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

    // This method invokes when the user successfully authenticates; it's configured in SecurityConfiguration,
    // via .oauth2Login().defaultSuccessUrl("/user/onLogin").
    @GetMapping(value = "/user/onLogin")
    public RedirectView onLogin() throws UsernameNotFoundException {
        // add new-user data into database (if not there already), and update lastLogin
        var user = (UserModel)upsertUser();
        initializeUser(user);

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
        return user;
    }

    private void initializeUser(UserModel user)
    {
        var userId = user.getId().toString();
        // TODO: make these DRY. Also, they should be across HTTPS, not HTTP.
        s2sClient.post("http://localhost:8081/turns", userId, String.class);
    }
}