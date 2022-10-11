package com.deengames.dungeonsofthesultanate.web.configuration;

import com.deengames.dungeonsofthesultanate.web.health.HealthController;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Order(99) // if 100+, breaks authz stuff
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final String[] unauthenticatedRoutes = new String[]
    {
        "/", // home page
        String.format("/%s", HealthController.ROOT_URL), // BASIC health check (root)
        "/webjars/**", "*.css", "*.png" // web files that shouldn't be locked behind authentication
    };

    @Override
    public void configure(WebSecurity web) {
        web
            .ignoring()
            .antMatchers(unauthenticatedRoutes);
    }

    // MUST be under configure(WebSecurity), see: https://stackoverflow.com/questions/56388865/spring-security-configuration-httpsecurity-vs-websecurity/56389047#56389047
    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
//            .csrf().disable().cors().disable()
            .authorizeRequests()
            .antMatchers(unauthenticatedRoutes).permitAll()
            .anyRequest().authenticated()
            .and()
            .oauth2Client()
            .and()
            .oauth2Login()
            .defaultSuccessUrl("/user/onLogin");
    }

    // Other Configurations...

}