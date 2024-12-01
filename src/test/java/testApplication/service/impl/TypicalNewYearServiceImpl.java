package testApplication.service.impl;

import org.example.di_container.annotation.Autowired;
import org.example.di_container.annotation.PostConstruct;
import org.example.di_container.annotation.PreDestroy;
import org.example.di_container.annotation.Service;
import testApplication.entity.Gift;
import testApplication.entity.Person;
import testApplication.service.*;

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
        System.out.printf("Предновогодний день начинается для %s%n", person.getName());
    }

    @PreDestroy
    public void preDestroy() {
        System.out.printf("%s идет спать и думать, что делать с подарком %n", person.getName());
    }

    @Override
    public void dayBeforeNewYear() {
        System.out.println("\n***ДООООБРОЕ УТРО, ДО НОВОГО ГОДА ОСТАЛОСЬ 14 ЧАСов*** \n");

        System.out.printf("Перед началом празнования, нашему %d летнему %s необходимо подготовить стишок) %n", person.getAge(), person.getName());
        poemService.preparePoem(person);

        System.out.println("Далее, необходимо приготовить салаты");
        saladService.prepareSalad();

        System.out.println("В ожидании нового года, нужно создать новогоднюю атмосферу");
        if (this.person.getAge() < 25) {
            System.out.printf("Поскольку %s очень молод и глуп, он пытается найти что-то кроме новогодних шоу на ТВ%n", person.getName());
            showService.watchShow("Anything");
            System.out.println();
        } else {
            System.out.printf("%s уже давно смирился с неизбежностью, он случайно лупит по кнопкам%n", person.getName());
            showService.watchShow("Рен тв");
        }

    }

    @Override
    public void celebrateNewYear() {
        System.out.println("***ДОБРЫЙ ВЕЧЕР, ДО НОВОГО ГОДА ОСТАЛОСЬ 3 ЧАСА*** \n\n");

        System.out.println("Настало время подарков");
        giftService.presentGift(person, gift);
        System.out.println("Что-же подарили нашему герою");
        this.gift.describeGift();

        System.out.println("Едим салаты и смотрим ТВ");
        saladService.eatSalad();

        System.out.println("Куранты");
        for (int i = 0; i < 3; i++) {
            fireworkService.start();
        }
    }

}
