package de.a9d3.testing.method;

import de.a9d3.testing.method_extractor.MethodExtractor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenericMatcher {
    private GenericMatcher() {
        // should not be initialized
    }

    public static List<MethodTuple> match(Class c, String aRegex, String bRegex) {
        List<Method> a = MethodExtractor.extract(c, aRegex);
        List<Method> b = MethodExtractor.extract(c, bRegex);

        List<MethodTuple> tuples = new ArrayList<>();

        a.stream().forEach(aMethod -> {
            Optional<Method> optionalBMethod = b.stream()
                    .filter(bMethod -> aMethod.getName().replaceAll(aRegex, "")
                            .equals(bMethod.getName().replaceFirst(bRegex, "")))
                    .findAny();

            optionalBMethod.ifPresent(bMethod -> tuples.add(new MethodTuple(aMethod, bMethod)));
        });

        return tuples;
    }
}
