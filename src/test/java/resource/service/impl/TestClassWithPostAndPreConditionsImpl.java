package resource.service.impl;

import resource.service.TestClassWithPostAndPreConditions;
import org.example.di_container.annotation.PostConstruct;
import org.example.di_container.annotation.PreDestroy;
import org.example.di_container.annotation.Service;

@Service
public class TestClassWithPostAndPreConditionsImpl implements TestClassWithPostAndPreConditions {

    @PostConstruct
    public void postConstruct() {
        throw new RuntimeException("postConstruct exception example");
    }

    @PreDestroy
    public void preDestroy() {
        throw new RuntimeException("preDestroy exception example");
    }
}
