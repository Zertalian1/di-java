package resource.service.impl;

import resource.service.TestSingletonClass;
import org.example.di_container.annotation.Scope;
import org.example.di_container.annotation.Service;
import org.example.di_container.enums.ScopeType;

@Service
@Scope(scope = ScopeType.singleton)
public class TestSingletonClassImpl implements TestSingletonClass {
}
