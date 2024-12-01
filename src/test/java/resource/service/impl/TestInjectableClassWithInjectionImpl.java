package resource.service.impl;

import resource.service.TestInjectableClassWithInjection;
import resource.service.TestClassWithPostAndPreConditions;
import org.example.di_container.annotation.Autowired;
import org.example.di_container.annotation.Service;

@Service
public class TestInjectableClassWithInjectionImpl implements TestInjectableClassWithInjection {

    @Autowired
    private TestClassWithPostAndPreConditions testClassWithPostAndPreConditions;

}
