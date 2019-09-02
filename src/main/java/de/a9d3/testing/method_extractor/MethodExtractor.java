package de.a9d3.testing.method_extractor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MethodExtractor {
    public static List<Method> extract(Class c, String regex) {
        Predicate<String> pattern = Pattern.compile(regex).asPredicate();
        return Arrays.stream(c.getMethods()).filter(method -> pattern.test(method.getName()))
                .collect(Collectors.toList());
    }
}
