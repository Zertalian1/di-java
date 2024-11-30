package org.example.di_container.context;

import lombok.Setter;
import lombok.SneakyThrows;
import org.example.di_container.annotation.*;
import org.example.di_container.enums.ScopeType;
import org.example.di_container.factory.DefaultBeanFactory;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {

    @Setter
    private DefaultBeanFactory beanFactory;
    private final Map<Class, Object> beanMap = new ConcurrentHashMap<>();
    private final Reflections scanner;

    public ApplicationContext(String packageToScan) {
        this.scanner = new Reflections(packageToScan);
    }

    public <T> T getBean(Class<T> clazz) {
        if (beanMap.containsKey(clazz)) {
            return (T) beanMap.get(clazz);
        }

        T bean = beanFactory.getBean(clazz);

        Scope annotation = bean.getClass().getAnnotation(Scope.class);
        if (annotation == null || annotation.scope() == ScopeType.singleton) {
            beanMap.put(clazz, bean);
        }

        return bean;
    }

    @SneakyThrows
    public <T> T getBean(Class<?> clazz, Method method) {

        if (beanMap.containsKey(method.getReturnType())) {
            return (T) beanMap.get(clazz);
        }

        Object instance = clazz.getDeclaredConstructor().newInstance();
        T bean = (T) method.invoke(instance);

        Scope annotation = bean.getClass().getAnnotation(Scope.class);
        if (annotation == null || annotation.scope() == ScopeType.singleton) {
            beanMap.put(clazz, bean);
        }

        return bean;
    }

    // Сделать мапу, какой бин в каком конфиге определён
    private void initConfigurations() {
        Set<Class<?>> configurations = scanner.getTypesAnnotatedWith(Configuration.class);
        for (Class<?> configuration : configurations) {
            Arrays.stream(configuration.getMethods())
                    .filter(method -> method.isAnnotationPresent(Bean.class))
                    .forEach(method -> getBean(configuration, method));
        }
    }


    public void start() {
        Set<Class<?>> services = scanner.getTypesAnnotatedWith(Service.class);
        for (Class<?> service : services) {
            getBean(service);
        }
    }

}
