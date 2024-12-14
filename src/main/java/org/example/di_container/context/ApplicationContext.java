package org.example.di_container.context;

import lombok.Setter;
import lombok.SneakyThrows;
import org.example.di_container.annotation.Bean;
import org.example.di_container.annotation.Configuration;
import org.example.di_container.annotation.Scope;
import org.example.di_container.annotation.Service;
import org.example.di_container.enums.ScopeType;
import org.example.di_container.factory.BeanFactory;
import org.example.di_container.interceptors.AutowiredInterceptor;
import org.example.di_container.processor.BeanProcessor;
import org.example.di_container.processor.PostConstructBeanProcessor;
import org.example.di_container.processor.PreDestroyBeanProcessor;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {

    @Setter
    private BeanFactory beanFactory;
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
        return getBean(clazz, null);
    }

    public <T> T getBean(Class<T> clazz, Set<Class> callStack) {

        T bean = (T) containsInstance(clazz);
        if (bean != null) {
            return bean;
        }

        synchronized (clazz) {

            bean = (T) containsInstance(clazz);

            if (bean != null) {
                return bean;
            }

            if (callStack == null) {
                callStack = new HashSet<>();
                callStack.add(clazz);
            } else {
                if (callStack.contains(clazz)) {
                    throw new RuntimeException("Cyclic dependency during initialization " + clazz.getSimpleName());
                }
            }


            bean = beanFactory.getBean(clazz, callStack);
            postConstructBeanProcessor.process(bean);
            //System.out.println(Thread.currentThread().getName() + " created bean " + bean.toString());

            if (bean.getClass().getAnnotation(Bean.class) != null || bean.getClass().getAnnotation(Service.class) != null) {
                Scope annotation = bean.getClass().getAnnotation(Scope.class);
                if (annotation == null || annotation.scope() == ScopeType.singleton) {
                    Class beanClass = bean.getClass();
                    if (bean.getClass().getAnnotation(Service.class) != null) {
                        bean = AutowiredInterceptor.getProxy(bean, this);
                    }
                    beanMap.put(beanClass, bean);
                    //System.out.println(Thread.currentThread().getName() + " put to  bean Map " + bean.toString() + " to  class " + clazz);
                }
            }
            createdBeans.put(bean.getClass(), bean);

            return bean;
        }
    }

    @SneakyThrows
    public <T> T getBean(Class<?> clazz, Object objectOwner, Method initMethod) {

        T bean = (T) containsInstance(clazz);
        if (bean != null) {
            return bean;
        }
        synchronized (clazz) {

            bean = (T) containsInstance(clazz);
            if (bean != null) {
                return bean;
            }

            bean = (T) initMethod.invoke(objectOwner);

            if (initMethod.getAnnotation(Bean.class) != null) {
                Scope annotation = initMethod.getAnnotation(Scope.class);
                if (annotation == null || annotation.scope() == ScopeType.singleton) {
                    beanMap.put(bean.getClass(), bean);
                }
            }
            createdBeans.put(bean.getClass(), bean);

            return bean;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void start() {
        if (isRunning) {
            stop();
        }
        isRunning = true;

        Set<Class<?>> configurations = scanner.getTypesAnnotatedWith(Configuration.class);
        for (Class<?> configuration : configurations) {
            var configurationBean = getBean(configuration);
            Arrays.stream(configuration.getMethods()).filter(method -> method.isAnnotationPresent(Bean.class)).forEach(method -> getBean(method.getReturnType(), configurationBean, method));
        }

        Set<Class<?>> services = scanner.getTypesAnnotatedWith(Service.class);
        for (Class<?> service : services) {
            getBean(service);
        }
    }

    public void stop() {
        for (Map.Entry<Class, Object> entry : createdBeans.entrySet()) {
            preDestroyBeanProcessor.process(entry.getValue());
        }
        createdBeans.clear();
        beanMap.clear();
        isRunning = false;
    }

    Object containsInstance(Class<?> clazz) {
        if (beanMap.containsKey(clazz)) {
            return beanMap.get(clazz);
        }
        for (Map. Entry<Class, Object> entry : beanMap.entrySet()) {
            if (clazz.isAssignableFrom(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

}
