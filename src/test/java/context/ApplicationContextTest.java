package context;

import lombok.SneakyThrows;
import org.example.di_container.context.ApplicationContext;
import org.example.di_container.factory.BeanFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.reflections.Reflections;
import resource.config.TestDefaultClassConfig;
import resource.service.TestDefaultClass;
import resource.service.TestPrototypeClass;
import resource.service.TestSingletonClass;
import resource.service.impl.TestDefaultClassImpl;
import resource.service.impl.TestPrototypeClassImpl;
import resource.service.impl.TestSingletonClassImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ApplicationContextTest {

    private BeanFactory beanFactory;
    private ApplicationContext applicationContext;
    private TestDefaultClassConfig config;

    @BeforeEach
    void beforeEach() {
        Reflections scaner = new Reflections("resource");
        beanFactory = Mockito.mock(BeanFactory.class);
        applicationContext = new ApplicationContext(scaner);
        applicationContext.setBeanFactory(beanFactory);
        config = new TestDefaultClassConfig();
    }

    @Test
    void getBeanByClassTest() {
        when(beanFactory.getBean(any())).thenReturn(new TestDefaultClassImpl());
        Object bean = applicationContext.getBean(TestDefaultClass.class);
        Assertions.assertInstanceOf(TestDefaultClassImpl.class, bean);
    }

    @Test
    void getBeanByClassTestBeanMap() {
        when(beanFactory.getBean(any())).thenReturn(new TestDefaultClassImpl());
        Object bean1 = applicationContext.getBean(TestDefaultClass.class);
        when(beanFactory.getBean(any())).thenReturn(new TestDefaultClassImpl());
        Object bean2 = applicationContext.getBean(TestDefaultClass.class);
        Assertions.assertInstanceOf(TestDefaultClassImpl.class, bean1);
        Assertions.assertInstanceOf(TestDefaultClassImpl.class, bean2);
        Assertions.assertSame(bean1, bean2);
    }

    @Test
    void getBeanByClassTestScopeSingleton() {
        when(beanFactory.getBean(any())).thenReturn(new TestSingletonClassImpl());
        Object bean1 = applicationContext.getBean(TestSingletonClass.class);
        Object bean2 = applicationContext.getBean(TestSingletonClass.class);
        Assertions.assertInstanceOf(TestSingletonClassImpl.class, bean1);
        Assertions.assertInstanceOf(TestSingletonClassImpl.class, bean2);
        Assertions.assertSame(bean1, bean2);
    }

    @Test
    void getBeanByClassTestScopePrototype() {
        when(beanFactory.getBean(any())).thenReturn(new TestPrototypeClassImpl());
        Object bean1 = applicationContext.getBean(TestPrototypeClass.class);
        when(beanFactory.getBean(any())).thenReturn(new TestPrototypeClassImpl());
        Object bean2 = applicationContext.getBean(TestPrototypeClass.class);
        Assertions.assertInstanceOf(TestPrototypeClassImpl.class, bean1);
        Assertions.assertInstanceOf(TestPrototypeClassImpl.class, bean2);
        Assertions.assertNotSame(bean1, bean2);
    }

    @Test
    @SneakyThrows
    void getBeanByInitMethodTest() {
        when(beanFactory.getBean(any())).thenReturn(new TestDefaultClassImpl());
        TestDefaultClassConfig config = new TestDefaultClassConfig();
        Object bean = applicationContext.getBean(TestDefaultClass.class, config, TestDefaultClassConfig.class.getMethod("initTestMethod"));
        Assertions.assertInstanceOf(TestDefaultClassImpl.class, bean);
    }

    @Test
    @SneakyThrows
    void getBeanByInitMethodTestBeanMap() {
        Object bean1 = applicationContext.getBean(TestDefaultClass.class, config, TestDefaultClassConfig.class.getMethod("initTestMethod"));
        Object bean2 = applicationContext.getBean(TestDefaultClass.class, config, TestDefaultClassConfig.class.getMethod("initTestMethod"));
        Assertions.assertInstanceOf(TestDefaultClassImpl.class, bean1);
        Assertions.assertInstanceOf(TestDefaultClassImpl.class, bean2);
        Assertions.assertSame(bean1, bean2);
    }

    @Test
    @SneakyThrows
    void getBeanByInitMethodTestScopeSingleton() {
        Object bean1 = applicationContext.getBean(TestDefaultClass.class, config, TestDefaultClassConfig.class.getMethod("initTestMethodSingleton"));
        Object bean2 = applicationContext.getBean(TestDefaultClass.class, config, TestDefaultClassConfig.class.getMethod("initTestMethodSingleton"));
        Assertions.assertInstanceOf(TestDefaultClassImpl.class, bean1);
        Assertions.assertInstanceOf(TestDefaultClassImpl.class, bean2);
        Assertions.assertSame(bean1, bean2);
    }

    @Test
    @SneakyThrows
    void getBeanByInitMethodTestScopePrototype() {
        Object bean1 = applicationContext.getBean(TestDefaultClass.class, config, TestDefaultClassConfig.class.getMethod("initTestMethodPrototype"));
        Object bean2 = applicationContext.getBean(TestDefaultClass.class, config, TestDefaultClassConfig.class.getMethod("initTestMethodPrototype"));
        Assertions.assertInstanceOf(TestDefaultClassImpl.class, bean1);
        Assertions.assertInstanceOf(TestDefaultClassImpl.class, bean2);
        Assertions.assertNotSame(bean1, bean2);
    }

    @Test
    void ApplicationContextStartTest() {
        when(beanFactory.getBean(any())).thenReturn(new TestDefaultClassConfig());
        applicationContext.start();
        Object createdBean = applicationContext.getBean(TestDefaultClass.class);
        Assertions.assertInstanceOf(TestDefaultClassImpl.class, createdBean);
        Assertions.assertTrue(applicationContext.isRunning());
    }

    @Test
    void ApplicationContextEndTest() {
        when(beanFactory.getBean(any())).thenReturn(new TestDefaultClassConfig());
        applicationContext.start();
        applicationContext.stop();
        Assertions.assertFalse(applicationContext.isRunning());
        when(beanFactory.getBean(any())).thenReturn(null);
        Assertions.assertThrows(NullPointerException.class, () -> applicationContext.getBean(TestDefaultClass.class));
    }

}
