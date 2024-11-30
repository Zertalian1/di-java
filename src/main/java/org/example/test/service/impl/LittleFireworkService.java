package org.example.test.service.impl;

import org.example.di_container.annotation.Service;
import org.example.test.service.FireworkService;

@Service
public class LittleFireworkService implements FireworkService {

    @Override
    public void start() {
        System.out.println("ПЩЩЩЩЩ, пуп");
    }

}
