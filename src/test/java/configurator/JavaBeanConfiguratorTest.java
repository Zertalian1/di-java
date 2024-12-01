package configurator;

import resource.service.TestClasWithInjection;
import resource.service.TestClassWithSeveralImplementations;
import resource.service.impl.TestClasWithInjectionImpl;
import org.example.di_container.configurator.BeanConfigurator;
import org.example.di_container.configurator.JavaBeanConfigurator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

class JavaBeanConfiguratorTest {

    private BeanConfigurator configurator;

    @BeforeEach
    void beforeEach() {
        Reflections scaner = new Reflections("resource");
        configurator = new JavaBeanConfigurator(scaner);
    }


    @Test
    void getImplementationClassTestOneImplementation() {
        Class impementation = configurator.getImplementationClass(TestClasWithInjection.class);
        Assertions.assertEquals(impementation.getName(), TestClasWithInjectionImpl.class.getName());
    }

    @Test
    void getImplementationClassTestSeveralImplementation() {
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> configurator.getImplementationClass(TestClassWithSeveralImplementations.class)) ;
        Assertions.assertEquals(thrown.getMessage(), "Several implementations found for %s".formatted(TestClassWithSeveralImplementations.class.getSimpleName()));
    }
}
