package org.example.test.service.impl;

import org.example.di_container.annotation.Service;
import org.example.test.service.ShowService;

@Service
public class RussianShowService implements ShowService {

    @Override
    public void watchShow(String chanel) {
        switch (chanel) {
            case "СТС" -> System.out.println("смотрим \"Уральские пельмени\"");
            case "Россия 1" -> System.out.println("смотрим \"Голубой огонёк\"");
            case "Рен тв" -> System.out.println("смотрим \"Ирония судьбы, или С лёгким паром!\"");
            default -> System.out.println("ЛОЛ, ТЫ НАДЕЯЛСЯ НА СПАСЕНИЕ) смотрим \"ЁЛКИ 1000\"");
        }
    }

}
