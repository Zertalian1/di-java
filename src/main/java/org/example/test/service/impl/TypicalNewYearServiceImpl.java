package org.example.test.service.impl;

import org.example.di_container.annotation.Autowired;
import org.example.di_container.annotation.PostConstruct;
import org.example.di_container.annotation.Service;
import org.example.test.entity.Person;
import org.example.test.entity.Gift;
import org.example.test.service.*;

@Service
public class TypicalNewYearServiceImpl implements NewYearService {

    @Autowired()
    private GiftService giftService;
    @Autowired
    private PoemService poemService;
    @Autowired
    private SaladService saladService;
    @Autowired
    private ShowService showService;
    //@Autowired
    //private FireworkService fireworkService;

    private Person person;
    private Gift gift;

    private static String SUCCESS_LINE = "Happy New Year";

    @PostConstruct
    public void postConstruct() {
        System.out.println(SUCCESS_LINE);
    }

    @Override
    public void dayBeforeNewYear() {
        this.person = Person.getSimlePerson();
        poemService.preparePoem(this.person);
        this.gift = giftService.buyGift();
        saladService.prepareSalad();

        if (this.person.getAge() < 25) {
            showService.watchShow("Anything");
        }
    }

    @Override
    public void celebrateNewYear() {
        showService.watchShow("Россия 1");
        //fireworkService.start();
        giftService.presentGift(this.person, this.gift);
    }

}
