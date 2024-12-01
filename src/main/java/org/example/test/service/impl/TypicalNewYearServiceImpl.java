package org.example.test.service.impl;

import org.example.di_container.annotation.Autowired;
import org.example.di_container.annotation.PostConstruct;
import org.example.di_container.annotation.PreDestroy;
import org.example.di_container.annotation.Service;
import org.example.test.entity.Person;
import org.example.test.entity.Gift;
import org.example.test.service.*;

@Service
public class TypicalNewYearServiceImpl implements NewYearService {

    @Autowired
    private GiftService giftService;
    @Autowired
    private PoemService poemService;
    @Autowired
    private SaladService saladService;
    @Autowired
    private ShowService showService;
    @Autowired
    private FireworkService fireworkService;

    private Person person;
    private Gift gift;

    @PostConstruct
    public void postConstruct() {
        person = Person.getSimlePerson();
        gift = giftService.buyGift();
        System.out.printf("Everyone wakes up, including a certain %s%n", person.getName());
    }

    @PreDestroy
    public void preDestroy() {
        System.out.printf("%s is going to sleep%n", person.getName());
    }

    @Override
    public void dayBeforeNewYear() {
        System.out.println("***GOOOD MORNING*** \n\n");

        System.out.printf("Let's take a look at a typical New Year for %d-year-old %s%n%n", person.getAge(), person.getName());
        poemService.preparePoem(person);

        System.out.println("Preparation of salads");
        saladService.prepareSalad();

        if (this.person.getAge() < 25) {
            System.out.printf("Because %s is very young, he is trying to escape from New Year's shows on TV%n", person.getName());
            showService.watchShow("Anything");
            System.out.println();
        }

    }

    @Override
    public void celebrateNewYear() {
        System.out.println("***GOOOD EVENING*** \n\n");

        giftService.presentGift(person, gift);
        System.out.println("What a gift it was");
        this.gift.describeGift();

        saladService.eatSalad();

        showService.watchShow("Россия 1");

        fireworkService.start();
    }

}
