package factory;

import org.example.di_container.context.ApplicationContext;
import org.example.di_container.factory.BeanFactory;
import org.example.di_container.factory.DefaultBeanFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.reflections.Reflections;
import resource.service.TestClasWithInjection;
import resource.service.impl.TestClasWithInjectionImpl;
import resource.service.impl.TestInjectableClassWithInjectionImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DefaultBeanFactoryTest {

    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;

    @BeforeEach
    void beforeEach() {
        Reflections scaner = new Reflections("resource");
        applicationContext = Mockito.mock(ApplicationContext.class);

        beanFactory = new DefaultBeanFactory(scaner, applicationContext);
    }

    @Test
    void getBeanTestInterface() {
        when(applicationContext.getBean(any())).thenReturn(new TestInjectableClassWithInjectionImpl());
        Object bean = beanFactory.getBean(TestClasWithInjection.class);
        Assertions.assertInstanceOf(TestClasWithInjectionImpl.class, bean);
    }

    @Test
    void getBeanTestObject() {
        when(applicationContext.getBean(any())).thenReturn(new TestInjectableClassWithInjectionImpl());
        Object bean = beanFactory.getBean(TestClasWithInjectionImpl.class);
        Assertions.assertInstanceOf(TestClasWithInjectionImpl.class, bean);
    }


}
