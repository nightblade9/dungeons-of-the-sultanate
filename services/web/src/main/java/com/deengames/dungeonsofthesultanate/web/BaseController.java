package com.deengames.dungeonsofthesultanate.web;

import com.deengames.dungeonsofthesultanate.dtos.PlayerStatsDto;
import com.deengames.dungeonsofthesultanate.web.security.SecurityContextFetcher;
import com.deengames.dungeonsofthesultanate.web.security.TokenParser;
import com.deengames.dungeonsofthesultanate.web.security.client.ServiceToServiceClient;
import com.deengames.dungeonsofthesultanate.web.users.ReadUserDetailsService;
import com.deengames.dungeonsofthesultanate.web.users.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;

public abstract class BaseController {

    @Autowired
    protected ReadUserDetailsService readUserDetailsService;

    @Autowired
    private SecurityContextFetcher securityContextFetcher;

    @Autowired
    private ServiceToServiceClient client;

    @Autowired
    private Environment environment;

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
        if (securityContextFetcher == null || securityContextFetcher.getAuthentication() == null) {
            return null;
        }

        var userEmail = this.getUserEmailFromToken();
        if (userEmail == null) {
            return null; // User email not found in token
        }

        var username = UserModel.calculateUserName(userEmail);
        var user = (UserModel)readUserDetailsService.loadUserByUsername(username);

        return user;
    }

    protected PlayerStatsDto getPlayerStats() {
        var user = this.getCurrentUser();

        if (user == null) {
            return null;
        }

        var playerServiceUrl = environment.getProperty("dots.serviceToService.playerService");
        var getPlayerUrl = String.format("%s/stats/%s", playerServiceUrl, user.getId());
        var player = client.get(getPlayerUrl, PlayerStatsDto.class);
        return player;
    }

    protected int getXpForNextLevel(int nextLevel) {
        var user = this.getCurrentUser();

        if (user == null) {
            return 0;
        }

        var playerServiceUrl = environment.getProperty("dots.serviceToService.playerService");
        var getPlayerUrl = String.format("%s/levelup/%s", playerServiceUrl, nextLevel);
        var result = client.get(getPlayerUrl, Integer.class);
        return result.intValue();
    }

    protected void addPlayerDetailsToModel(Model model) {
        var playerStats = this.getPlayerStats();

        model.addAttribute("currentUser", this.getCurrentUser());
        model.addAttribute("currentStats", playerStats);
        model.addAttribute("xpRequiredForNextLevel", this.getXpForNextLevel(playerStats.getLevel() + 1));
    }
}
