package org.example.test;

import org.example.di_container.Application;
import org.example.di_container.context.ApplicationContext;
import org.example.test.service.NewYearService;

public class Test {


    public static void main(String[] args) {
        ApplicationContext context = Application.run(Test.class);

        NewYearService newYearService = context.getBean(NewYearService.class);
        newYearService.dayBeforeNewYear();
        newYearService.celebrateNewYear();
    }
}