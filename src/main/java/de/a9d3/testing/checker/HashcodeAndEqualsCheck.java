package de.a9d3.testing.checker;

import de.a9d3.testing.method_extractor.GetterIsSetterExtractor;
import de.a9d3.testing.testdata.TestDataProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;


public class HashcodeAndEqualsCheck implements CheckerInterface {

    private TestDataProvider provider;

    public HashcodeAndEqualsCheck() {
        this(new TestDataProvider());
    }

    public HashcodeAndEqualsCheck(TestDataProvider provider) {
        this.provider = provider;
    }


    @Override
    public boolean check(Class c)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<Method> setterList = GetterIsSetterExtractor.getSetter(c).stream()
                // try to generate data for each setter, filter out which do not work
                .filter(this::filterForFillableData).collect(Collectors.toList());

        Object a = provider.fillMutableWithNull(c);
        Object b = provider.fillMutableWithNull(c);

        int iter = 0;

        if (!haveEqualHashCode(c, a, b)) {
            return false;
        }

        for (Method mySetter : setterList) {
            executeSetter(mySetter, a, iter);

            // Check same object
            if (!(areEqual(a, a))) {
                return false;
            }

            // Check different class. Guarantee to always use a different class
            // Nothing special about String, it's just not Object
            if (areEqual(a, mySetter.getParameterTypes()[0].equals(Object.class) ? String.class : Object.class)) {
                return false;
            }

            // Should not equal
            if (areEqual(a, b) || haveEqualHashCode(c, a, b)) {
                return false;
            }

            // Set setter in second object
            executeSetter(mySetter, b, iter);

            // Should equal
            if (!areEqual(a, b) || !haveEqualHashCode(c, a, b)) {
                return false;
            }

            iter+=1;
        }

        return true;
    }

    private boolean areEqual(Object a, Object b) {
        return a.equals(b);
    }

    private boolean haveEqualHashCode(Class c, Object a, Object b)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method hashCode = c.getMethod("hashCode");

        return hashCode.invoke(a).equals(hashCode.invoke(b));
    }

    private void executeSetter(Method m, Object o, int iter)
            throws IllegalAccessException, InvocationTargetException {
        Object input;

        if (m.getParameterTypes()[0].equals(boolean.class)) {
            // always set true as default for boolean is false
            input = true;
        } else {
            input = provider.fill(m.getParameterTypes()[0], "f50c83cf-5b60-4b2b-a869-b99bb0d130b9" + iter,
                    false);
        }

        m.invoke(o, input);
    }

    private boolean filterForFillableData(Method setter) {
        return provider.fill(setter.getParameterTypes()[0], "123", false) != null;
    }
}
