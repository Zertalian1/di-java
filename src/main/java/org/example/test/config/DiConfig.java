package org.example.test.config;

import org.example.di_container.annotation.Autowired;
import org.example.di_container.annotation.Bean;
import org.example.di_container.annotation.Configuration;
import org.example.test.service.FireworkService;
import org.example.test.service.NewYearShopService;
import org.example.test.service.impl.LittleFireworkService;

@Configuration
public class DiConfig {

    @Autowired
    private NewYearShopService shopService;

    @Bean
    public FireworkService createFireworkService() {
        shopService.buy();
        return new LittleFireworkService();
    }

}
