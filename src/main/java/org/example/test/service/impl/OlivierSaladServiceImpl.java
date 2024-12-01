package org.example.test.service.impl;

import org.example.di_container.annotation.Autowired;
import org.example.di_container.annotation.Service;
import org.example.test.entity.Salad;
import org.example.test.service.SaladService;
import org.example.test.service.ShowService;

@Service
public class OlivierSaladServiceImpl implements SaladService {

    private Salad salad;

    @Autowired
    private ShowService showService;

    @Override
    public void prepareSalad() {
        showService.watchShow("СТС");
        System.out.println("Cooking olivier");
        this.salad = new Salad("Olivier");
    }

    @Override
    public void eatSalad() {
        System.out.printf("Eating %s%n", this.salad.getName());
        showService.watchShow("Россия 1");
    }

}
