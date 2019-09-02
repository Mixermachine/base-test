package de.a9d3.testing.method;

import java.util.List;

public class IsSetterMatcher implements MethodMatcherInterface {
    @Override
    public List<MethodTuple> match(Class c) {
        return GenericMatcher.match(c, "^is", "^set");
    }
}
