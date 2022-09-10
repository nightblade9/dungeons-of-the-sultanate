package com.deengames.dungeonsofthesultanate.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class HomeController
{
    // TODO: can move to microservice, base controller, etc.
    public String getCurrentUser()
    {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
        {
            return null;
        }

        var token = (OAuth2AuthenticationToken)authentication;
        if (token == null)
        {
            // Not a GitHub? OAUth2 token ...
            return null;
        }
        // "login" is what GitHub OAuth2 gives us
        var userName = token.getPrincipal().getAttributes().get("login").toString();
        return userName;
    }

    @GetMapping("/")
    public String index(Model model)
    {
        model.addAttribute("authenticatedAs", getCurrentUser());
        return "index"; // index.html
    }
}
