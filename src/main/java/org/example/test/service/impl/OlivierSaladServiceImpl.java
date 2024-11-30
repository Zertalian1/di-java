package org.example.test.service.impl;

import org.example.di_container.annotation.Autowired;
import org.example.di_container.annotation.Service;
import org.example.test.service.SaladService;
import org.example.test.service.ShowService;

@Service
public class OlivierSaladServiceImpl implements SaladService {

    @Autowired
    private ShowService showService;

    @Override
    public void prepareSalad() {
        showService.watchShow("СТС");
    }

    @Override
    public void eatSalad() {
        showService.watchShow("Россия 1");
    }

}
