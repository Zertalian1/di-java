package org.example.di_container.processor;

import lombok.SneakyThrows;
import org.example.di_container.annotation.PostConstruct;

import java.lang.reflect.Method;

public class PostConstructBeanProcessor implements BeanProcessor {

    @Override
    @SneakyThrows
    public void process(Object bean) {
        for (Method method : bean.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.invoke(bean);
            }
        }
    }

}
