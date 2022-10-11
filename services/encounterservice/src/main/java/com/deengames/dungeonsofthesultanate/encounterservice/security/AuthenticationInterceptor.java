package com.deengames.dungeonsofthesultanate.encounterservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * An interceptor that checks that all incoming requests are authenticated (via the shared secret).
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private Environment environment;

    public AuthenticationInterceptor() {
    }

    public AuthenticationInterceptor(String secret) {
        this.expectedSecret = secret;
    }

    @Value("${dots.serviceToService.secret}")
    private String expectedSecret;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (expectedSecret == null || expectedSecret.length() == 0)
        {
            response.setStatus(500);
            throw new SecurityException("Service is missing configuration for client secret!");
        }

        var clientSecret = request.getHeader("Client-Secret");
        if (!expectedSecret.equals(clientSecret))
        {
            response.setStatus(401);
            response.getWriter().write("unauthorized");
            return false;
        }

        return true;
    }
}
