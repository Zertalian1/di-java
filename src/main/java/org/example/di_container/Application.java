package org.example.di_container;

import org.example.di_container.context.ApplicationContext;
import org.example.di_container.factory.DefaultBeanFactory;
import org.reflections.Reflections;

public class Application {

    public static ApplicationContext run(Class<?> clazz) {
        Reflections scanner = new Reflections(clazz.getPackageName());
        ApplicationContext applicationContext = new ApplicationContext(scanner);
        DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory(scanner, applicationContext);
        applicationContext.setBeanFactory(defaultBeanFactory);
        return applicationContext;
    }

}
