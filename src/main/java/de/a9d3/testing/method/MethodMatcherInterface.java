package de.a9d3.testing.method;

import java.util.List;

public interface MethodMatcherInterface {
    List<MethodTuple> match(Class c);
}
