package processor;

import org.example.di_container.processor.BeanProcessor;
import org.example.di_container.processor.PostConstructBeanProcessor;
import org.example.di_container.processor.PreDestroyBeanProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import resource.service.TestClassWithPostAndPreConditions;
import resource.service.impl.TestClassWithPostAndPreConditionsImpl;

import java.lang.reflect.InvocationTargetException;

public class PreDestroyBeanProcessorTest {

    private BeanProcessor beanProcessor;

    @BeforeEach
    void beforeEach() {
        beanProcessor = new PreDestroyBeanProcessor();
    }

    @Test
    void testPostConstruct() {
        TestClassWithPostAndPreConditions bean = new TestClassWithPostAndPreConditionsImpl();
        InvocationTargetException thrown = Assertions.assertThrows(InvocationTargetException.class, () -> beanProcessor.process(bean));
        Assertions.assertEquals(thrown.getTargetException().getMessage(), "preDestroy exception example");
    }

}
