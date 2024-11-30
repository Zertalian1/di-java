package org.example.di_container;

import org.example.di_container.context.ApplicationContext;
import org.example.di_container.factory.DefaultBeanFactory;

public class Application {

    public static ApplicationContext run(Class<?> clazz) {
        ApplicationContext applicationContext = new ApplicationContext(clazz.getPackageName());
        DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory(clazz.getPackageName(), applicationContext);
        applicationContext.setBeanFactory(defaultBeanFactory);
        applicationContext.start();
        return applicationContext;
    }

}
