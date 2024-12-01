package resource.config;

import org.example.di_container.annotation.Bean;
import org.example.di_container.annotation.Configuration;
import org.example.di_container.annotation.Scope;
import org.example.di_container.enums.ScopeType;
import resource.service.TestDefaultClass;
import resource.service.impl.TestDefaultClassImpl;

@Configuration
public class TestDefaultClassConfig {

    @Bean
    public TestDefaultClass initTestMethod() {
        return new TestDefaultClassImpl();
    }

    @Bean
    @Scope(scope = ScopeType.singleton)
    public TestDefaultClass initTestMethodSingleton() {
        return new TestDefaultClassImpl();
    }

    @Bean
    @Scope(scope = ScopeType.prototype)
    public TestDefaultClass initTestMethodPrototype() {
        return new TestDefaultClassImpl();
    }

}
