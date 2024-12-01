package testApplication.service.impl;

import org.example.di_container.annotation.Service;
import testApplication.service.NewYearShopService;

@Service
public class FireworkShopService implements NewYearShopService {

    @Override
    public void buy() {
        System.out.println("Покупаем фейверки");
    }

}
