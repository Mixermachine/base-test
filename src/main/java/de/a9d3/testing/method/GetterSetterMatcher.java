package de.a9d3.testing.method;

import de.a9d3.testing.tuple.MethodTuple;

import java.util.List;

public class GetterSetterMatcher implements MethodMatcherInterface {
    @Override
    public List<MethodTuple> match(Class<?> c) {
        return GenericMatcher.match(c, "^get", "^set");
    }
}
