package resource.service.impl;

import resource.service.TestClassWithSeveralImplementations;
import org.example.di_container.annotation.Service;

@Service
public class TestClassWithSeveralImplementationsSecond implements TestClassWithSeveralImplementations {
    @Override
    public void testMethod() {

    }
}
