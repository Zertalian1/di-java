package org.example.test.service.impl;

import org.example.di_container.annotation.Service;
import org.example.test.entity.Person;
import org.example.test.service.PoemService;

@Service
public class DedMorozPoemServiceImpl implements PoemService {
    private static final String TEEN_RESP = "Я слишком зрослый для всей этой фигни";
    private static final String ADULT_RESP = "Матерные частушки";
    private static final String CHILD_RESP = "Здравствуй, Дедушка Мороз!";

    @Override
    public void preparePoem(Person child) {
        if (child.getAge() > 30) {
            System.out.println(ADULT_RESP);
        } else if (child.getAge() > 20) {
            System.out.println(TEEN_RESP);
        } else {
            System.out.println(CHILD_RESP);
        }
    }

}
