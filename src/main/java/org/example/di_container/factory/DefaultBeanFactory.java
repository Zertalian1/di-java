package org.example.di_container.factory;

import lombok.SneakyThrows;
import org.example.di_container.annotation.Autowired;
import org.example.di_container.configurator.BeanConfigurator;
import org.example.di_container.configurator.JavaBeanConfigurator;
import org.example.di_container.context.ApplicationContext;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class DefaultBeanFactory implements BeanFactory {

    private final BeanConfigurator beanConfigurator;
    private final ApplicationContext applicationContext;

    public DefaultBeanFactory(String packageToScan, ApplicationContext applicationContext) {
        this.beanConfigurator = new JavaBeanConfigurator(packageToScan);
        this.applicationContext = applicationContext;
    }

    @Override
    @SneakyThrows
    public <T> T getBean(Class<T> clazz) {
        final Class<? extends T> implementationClass = clazz.isInterface()
                ? beanConfigurator.getImplementationClass(clazz)
                : clazz;

        T bean = implementationClass.getDeclaredConstructor().newInstance();

        List<Field> filteredFields = Arrays.stream(implementationClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Autowired.class))
                .toList();

        for (Field field : filteredFields) {
            field.setAccessible(true);
            field.set(bean, applicationContext.getBean(field.getType()));
        }

        return bean;
    }

}
