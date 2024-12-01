package org.example.di_container.context;

import lombok.Setter;
import lombok.SneakyThrows;
import org.example.di_container.annotation.*;
import org.example.di_container.enums.ScopeType;
import org.example.di_container.factory.DefaultBeanFactory;
import org.example.di_container.processor.BeanProcessor;
import org.example.di_container.processor.PostConstructBeanProcessor;
import org.example.di_container.processor.PreDestroyBeanProcessor;
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
    private final Map<Class, Object> createdBeans = new ConcurrentHashMap<>();
    private final BeanProcessor postConstructBeanProcessor = new PostConstructBeanProcessor();
    private final BeanProcessor preDestroyBeanProcessor = new PreDestroyBeanProcessor();
    private final Reflections scanner;
    private boolean isRunning;

    public ApplicationContext(Reflections scanner) {
        this.scanner = scanner;
    }

    public <T> T getBean(Class<T> clazz) {
        if (beanMap.containsKey(clazz)) {
            return (T) beanMap.get(clazz);
        }

        T bean = beanFactory.getBean(clazz);
        postConstructBeanProcessor.process(bean);

        if (bean.getClass().getAnnotation(Bean.class) != null || bean.getClass().getAnnotation(Service.class) != null) {
            Scope annotation = bean.getClass().getAnnotation(Scope.class);
            if (annotation == null || annotation.scope() == ScopeType.singleton) {
                beanMap.put(clazz, bean);
            }
        }
        createdBeans.put(clazz, bean);

        return bean;
    }

    @SneakyThrows
    public <T> T getBean(Class<?> clazz, Object objectOwner,  Method initMethod) {

        if (beanMap.containsKey(clazz)) {
            return (T) beanMap.get(clazz);
        }

        T bean = (T) initMethod.invoke(objectOwner);

        Scope annotation = bean.getClass().getAnnotation(Scope.class);
        if (annotation == null || annotation.scope() == ScopeType.singleton) {
            beanMap.put(clazz, bean);
        }
        createdBeans.put(clazz, bean);

        return bean;
    }

    public void start() {
        if (isRunning) {
            stop();
        }
        isRunning = true;

        Set<Class<?>> configurations = scanner.getTypesAnnotatedWith(Configuration.class);
        for (Class<?> configuration : configurations) {
            var configurationBean = getBean(configuration);
            Arrays.stream(configuration.getMethods())
                    .filter(method -> method.isAnnotationPresent(Bean.class))
                    .forEach(method -> getBean(method.getReturnType(), configurationBean, method));
        }
    }

    public  void stop() {
        for (Map.Entry<Class, Object> entry : createdBeans.entrySet()) {
            preDestroyBeanProcessor.process(entry.getValue());
        }
        createdBeans.clear();
        beanMap.clear();
        isRunning = false;
    }

}
