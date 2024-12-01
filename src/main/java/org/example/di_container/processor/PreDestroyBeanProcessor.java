package org.example.di_container.processor;

import lombok.SneakyThrows;
import org.example.di_container.annotation.PreDestroy;

import java.lang.reflect.Method;

public class PreDestroyBeanProcessor implements BeanProcessor {

    @Override
    @SneakyThrows
    public void process(Object bean) {
        for (Method method : bean.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(PreDestroy.class)) {
                method.invoke(bean);
            }
        }
    }

}
