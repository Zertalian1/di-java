package org.example.test.config;

import org.example.di_container.annotation.Bean;
import org.example.di_container.annotation.Configuration;
import org.example.test.service.FireworkService;
import org.example.test.service.impl.LittleFireworkService;

@Configuration
public class DiConfig {

    @Bean
    public FireworkService createFireworkService() {
        return new LittleFireworkService();
    }

}
