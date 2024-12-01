package testApplication;

import org.example.di_container.Application;
import org.example.di_container.context.ApplicationContext;
import testApplication.service.NewYearService;

public class Test {


    public static void main(String[] args) {
        ApplicationContext context = Application.run(Test.class);

        context.start();
        NewYearService newYearService = context.getBean(NewYearService.class);
        newYearService.dayBeforeNewYear();
        newYearService.celebrateNewYear();
        context.stop();
    }
}