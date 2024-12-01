package org.example.di_container.configurator;

import org.reflections.Reflections;

import java.util.Set;

public class JavaBeanConfigurator implements BeanConfigurator {

    private final Reflections scanner;

    public JavaBeanConfigurator(Reflections scanner) {
        this.scanner = scanner;

    }

    @Override
    public <T> Class<? extends T> getImplementationClass(Class<T> interfaceClass) {
        Set<Class<? extends T>> implementationClasses = scanner.getSubTypesOf(interfaceClass);
        if (implementationClasses.size() != 1) {
            throw new RuntimeException("Several implementations found for %s".formatted(interfaceClass.getSimpleName()));
        }

        return implementationClasses.stream().findFirst().get();
    }

}
