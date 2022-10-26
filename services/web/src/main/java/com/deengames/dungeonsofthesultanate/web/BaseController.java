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

    // Cached so multiple requests per controller action don't result in multiple expensive calls
    private UserModel currentPlayer;
    private PlayerStatsDto currentPlayerStats;

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

        if (this.currentPlayer != null) {
            return this.currentPlayer;
        }

        var userEmail = this.getUserEmailFromToken();
        if (userEmail == null) {
            return null; // User email not found in token
        }

        var username = UserModel.calculateUserName(userEmail);
        var user = (UserModel)readUserDetailsService.loadUserByUsername(username);
        this.currentPlayer = user;

        return user;
    }

    protected PlayerStatsDto getPlayerStats() {
        if (this.currentPlayerStats != null)
        {
            return this.currentPlayerStats;
        }

        var user = this.getCurrentUser();

        if (user == null) {
            return null;
        }

        var playerServiceUrl = environment.getProperty("dots.serviceToService.playerService");
        var getPlayerUrl = String.format("%s/stats/%s", playerServiceUrl, user.getId());
        var player = client.get(getPlayerUrl, PlayerStatsDto.class);
        this.currentPlayerStats = player;
        return player;
    }

    protected void addPlayerDetailsToModel(Model model) {
        model.addAttribute("currentUser", this.getCurrentUser());
        model.addAttribute("currentStats", this.getPlayerStats());
    }
}
