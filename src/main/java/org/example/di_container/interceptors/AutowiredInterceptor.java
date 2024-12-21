package org.example.di_container.interceptors;

import org.example.di_container.annotation.Autowired;
import org.example.di_container.context.ApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;

public class AutowiredInterceptor<T> implements InvocationHandler {

    private T t;
    private ApplicationContext applicationContext;

    public AutowiredInterceptor(T t, ApplicationContext applicationContext) {
        this.t = t;
        this.applicationContext = applicationContext;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // Получаем параметры метода
        Parameter[] parameters = t.getClass().getMethod(method.getName(), method.getParameterTypes()).getParameters();

        // Проверяем параметры на наличие аннотации
        for (int i = 0; i < parameters.length && parameters.length == args.length; i++) {
            if (parameters[i].isAnnotationPresent(Autowired.class)) {
                args[i] = applicationContext.getBean(parameters[i].getType());
            }
        }

        // Вызываем оригинальный метод
        return method.invoke(t, args);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getProxy(T t, ApplicationContext applicationContext) {
        AutowiredInterceptor handler = new AutowiredInterceptor(t, applicationContext);
        ClassLoader classLoader = t.getClass().getClassLoader();
        Class[] interfaces = t.getClass().getInterfaces();
        return (T) Proxy.newProxyInstance(classLoader, interfaces, handler);
    }
}