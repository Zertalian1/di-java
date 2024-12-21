package org.example.di_container.factory;

import java.util.Set;

public interface BeanFactory {

    <T> T getBean(Class<T> clazz, Set<Class> callStack);

    <T> T getBean(Class<T> clazz);
}
