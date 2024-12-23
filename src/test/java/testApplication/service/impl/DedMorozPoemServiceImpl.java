package testApplication.service.impl;

import org.example.di_container.annotation.Service;
import testApplication.entity.Person;
import testApplication.service.PoemService;

@Service
public class DedMorozPoemServiceImpl implements PoemService {
    private static final String TEEN_RESP = "Я слишком зрослый для всей этой фигни";
    private static final String ADULT_RESP = "Матерные частушки";
    private static final String CHILD_RESP = "Здравствуй, Дедушка Мороз!";

    @Override
    public void preparePoem(Person person) {
        System.out.printf("%s подготавливает стишок для Деда Мороза %n", person.getName());
        if (person.getAge() > 30) {
            System.out.println(ADULT_RESP);
        } else if (person.getAge() > 20) {
            System.out.println(TEEN_RESP);
        } else {
            System.out.println(CHILD_RESP);
        }
        System.out.println();
    }

}
