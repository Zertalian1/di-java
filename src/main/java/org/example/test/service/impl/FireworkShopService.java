package org.example.test.service.impl;

import org.example.di_container.annotation.Service;
import org.example.test.service.NewYearShopService;

@Service
public class FireworkShopService implements NewYearShopService {

    @Override
    public void buy() {
        System.out.println("Buying fireworks");
    }

}
