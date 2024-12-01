package testApplication.service.impl;

import org.example.di_container.annotation.Autowired;
import org.example.di_container.annotation.Service;
import testApplication.entity.Salad;
import testApplication.service.SaladService;
import testApplication.service.ShowService;

@Service
public class OlivierSaladServiceImpl implements SaladService {

    private Salad salad;

    @Autowired
    private ShowService showService;

    @Override
    public void prepareSalad() {
        System.out.println("Резня салатов");
        showService.watchShow("СТС");
        System.out.println("Получился тазик Оливье");
        this.salad = new Salad("Оливье");
    }

    @Override
    public void eatSalad() {
        System.out.printf("Поедаем тазик %s%n", this.salad.getName());
        showService.watchShow("Россия 1");
    }

}
