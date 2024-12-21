package context;

import lombok.Getter;
import lombok.Setter;
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

import java.util.Set;

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

    public class TestThreadClass implements Runnable {
        @Getter
        private volatile TestDefaultClass value;
        @Setter
        private ApplicationContext applicationContext;

        public volatile TestDefaultClass test;

        @Override
        public void run() {
            System.out.println("Start!");
            this.value = applicationContext.getBean(TestDefaultClass.class);
            System.out.println("Stop! " + this.value);
            test = value;
        }
    }

    @Test
    @SneakyThrows
    void ApplicationContextThreadTest() {
        BeanFactory beanFactoryLocal = new BeanFactory() {
            @Override
            public <T> T getBean(Class<T> clazz, Set<Class> callStack) {
                return getBean(clazz);
            }

            @Override
            public <T> T getBean(Class<T> clazz) {
                return (T) new TestDefaultClassImpl();
            }
        };

        applicationContext.setBeanFactory(beanFactoryLocal);

        TestThreadClass testThread1 = new TestThreadClass();
        testThread1.setApplicationContext(applicationContext);
        Thread thread1 = new Thread(testThread1);
        thread1.setName("thread1");

        TestThreadClass testThread2 = new TestThreadClass();
        testThread2.setApplicationContext(applicationContext);
        Thread thread2 = new Thread(testThread2);
        thread2.setName("thread2");

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        TestDefaultClass value1 = testThread1.getValue();
        TestDefaultClass value2 = testThread2.getValue();

        Assertions.assertEquals(value1, value2);
    }

    @Test
    void Test() {
        Class<?> clazz1 = TestDefaultClassImpl.class;
        Class<?> clazz2 = TestDefaultClass.class;

        TestDefaultClass clazz3 = new TestDefaultClassImpl();
        System.out.println();
    }

}
