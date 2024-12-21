package resource.service.impl;

import resource.service.TestPrototypeClass;
import org.example.di_container.annotation.Scope;
import org.example.di_container.annotation.Service;
import org.example.di_container.enums.ScopeType;

@Service
@Scope(scope = ScopeType.prototype)
public class TestPrototypeClassImpl implements TestPrototypeClass {
}
