package com.deengames.dungeonsofthesultanate.encounterservice.security;

import com.deengames.dungeonsofthesultanate.encounterservice.health.HealthController;
import com.deengames.dungeonsofthesultanate.turnservice.health.HealthController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfiguration implements WebMvcConfigurer {

    @Autowired
    private AuthenticationInterceptor interceptor;

    private final String[] unauthenticatedRoutes = new String[]
    {
        String.format("/%s", HealthController.ROOT_URL) // BASIC health check (root)
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns(unauthenticatedRoutes);
    }
}
