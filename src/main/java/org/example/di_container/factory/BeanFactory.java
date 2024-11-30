package org.example.di_container.factory;

public interface BeanFactory {

    <T> T getBean(Class<T> clazz);

}
