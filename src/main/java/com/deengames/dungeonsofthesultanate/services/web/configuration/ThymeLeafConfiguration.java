package com.deengames.dungeonsofthesultanate.services.web.configuration;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Bean;

public class ThymeLeafConfiguration {
    @Bean
    public LayoutDialect layoutDialect()
    {
        return new LayoutDialect();
    }

}
