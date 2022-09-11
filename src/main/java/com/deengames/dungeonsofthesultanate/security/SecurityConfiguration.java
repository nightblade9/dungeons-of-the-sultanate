package com.deengames.dungeonsofthesultanate.security;

import com.deengames.dungeonsofthesultanate.health.HealthController;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Order(99)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    // Other Configurations...

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/", String.format("/%s", HealthController.ROOT_URL));
    }

    // MUST be under configure(WebSecurity), see: https://stackoverflow.com/questions/56388865/spring-security-configuration-httpsecurity-vs-websecurity/56389047#56389047
    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
//                .csrf().disable().cors().disable()
                .authorizeRequests()
                .antMatchers("/", String.format("/%s", HealthController.ROOT_URL)).permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Client()
                .and()
                .oauth2Login()
                .defaultSuccessUrl("/user/onLogin");
    }

    // Other Configurations...

}