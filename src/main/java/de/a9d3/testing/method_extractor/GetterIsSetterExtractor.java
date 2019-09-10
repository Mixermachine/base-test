package de.a9d3.testing.method_extractor;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetterIsSetterExtractor {
    private GetterIsSetterExtractor() {
        // should not be invoke for now
    }

    public static List<Method>  getGetter(Class c) {
        return MethodExtractor.extract(c, "^get");
    }

    public static List<Method> getIs(Class c) {
        return MethodExtractor.extract(c, "^is");
    }

    public static List<Method> getGetterAndIs(Class c) {
        return Stream.concat(getGetter(c).stream(), getIs(c).stream()).collect(Collectors.toList());
    }

    public static List<Method> getSetter(Class c) {
        return MethodExtractor.extract(c, "^set");
    }
}
