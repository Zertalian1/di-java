package testApplication.config;

import org.example.di_container.annotation.Autowired;
import org.example.di_container.annotation.Bean;
import org.example.di_container.annotation.Configuration;
import testApplication.service.FireworkService;
import testApplication.service.NewYearShopService;
import testApplication.service.impl.LittleFireworkService;

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
