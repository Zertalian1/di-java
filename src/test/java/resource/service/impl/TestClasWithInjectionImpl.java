package resource.service.impl;

import resource.service.TestClasWithInjection;
import resource.service.TestInjectableClassWithInjection;
import org.example.di_container.annotation.Autowired;
import org.example.di_container.annotation.Service;

@Service
public class TestClasWithInjectionImpl implements TestClasWithInjection {

    private static final String testString = "TEST STRING";

    @Autowired
    private TestInjectableClassWithInjection testInjectableClassWithInjection;

    @Override
    public void testMethod() {}
}
