package com.deengames.dungeonsofthesultanate.services.web;

import com.deengames.dungeonsofthesultanate.services.web.security.SecurityContextFetcher;
import com.deengames.dungeonsofthesultanate.services.web.security.TokenParser;
import com.deengames.dungeonsofthesultanate.services.web.security.client.ServiceToServiceClient;
import com.deengames.dungeonsofthesultanate.services.web.users.ReadUserDetailsService;
import com.deengames.dungeonsofthesultanate.services.web.users.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

public abstract class BaseController {

    @Autowired
    protected ReadUserDetailsService readUserDetailsService;

    @Autowired
    private SecurityContextFetcher securityContextFetcher;

    protected String getUserEmailFromToken()
    {
        var authentication = securityContextFetcher.getAuthentication();
        var userEmail = TokenParser.getUserEmailAddressFromToken(authentication);
        if (userEmail == null)
        {
            throw new UsernameNotFoundException("Couldn't get user email address from claim!");
        }
        return userEmail;
    }

    protected UserModel getCurrentUser() {
        var userEmail = this.getUserEmailFromToken();
        if (userEmail == null)
        {
            throw new UsernameNotFoundException("User email not found in token.");
        }

        var username = UserModel.calculateUserName(userEmail);
        var user = (UserModel)readUserDetailsService.loadUserByUsername(username);

        return user;
    }
}
