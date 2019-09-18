package de.a9d3.testing.checker;

import de.a9d3.testing.GlobalStatics;
import de.a9d3.testing.checker.exception.CheckerHelperFunctions;
import de.a9d3.testing.method_extractor.GetterIsSetterExtractor;
import de.a9d3.testing.testdata.TestDataProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class HashcodeAndEqualsCheck implements CheckerInterface {
    private static final Logger LOGGER = Logger.getLogger(HashcodeAndEqualsCheck.class.getName());

    private TestDataProvider provider;

    public HashcodeAndEqualsCheck() {
        this(new TestDataProvider());
    }

    public HashcodeAndEqualsCheck(TestDataProvider provider) {
        this.provider = provider;
    }

    private static boolean defaultObjectsShouldBeEqualToAnother(Class c, Object a, Object b) {
        try {
            if (!(areEqual(a, b) && haveEqualHashCode(c, a, b))) {
                CheckerHelperFunctions.logFailedCheckerStep(LOGGER, "Default object should equal",
                        "Objects with internal null variables did not equal.");

                return true;
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            CheckerHelperFunctions.logFailedCheckerStep(LOGGER, c, e);
        }
        return false;
    }

    // mask so we have two methods we can call to check for correctness
    private static boolean areEqual(Object a, Object b) {
        return a.equals(b);
    }

    private static boolean haveEqualHashCode(Class c, Object a, Object b)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method hashCode = c.getMethod("hashCode");

        return hashCode.invoke(a).equals(hashCode.invoke(b));
    }

    @Override
    public boolean check(Class c) {
        List<Method> setterList = GetterIsSetterExtractor.getSetter(c).stream()
                // try to generate data for each setter, filter out which do not work
                .filter(this::filterForFillableData).collect(Collectors.toList());

        try {
            // Initialize objects with as many internal null variables as possible
            Object a = provider.fillMutableWithNull(c);
            Object b = provider.fillMutableWithNull(c);

            int iter = 0;


            if (defaultObjectsShouldBeEqualToAnother(c, a, b)) {
                return false;
            }


            for (Method mySetter : setterList) {
                if (checkIfEqualsAndHashcodeReactToSetter(c, a, b, iter, mySetter)) {
                    return false;
                }

                iter += 1;

            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            CheckerHelperFunctions.logFailedCheckerStep(LOGGER, c,
                    "Could not initialize class with internal null variables. You might need a custom " +
                            "TestDataProvider. See " + GlobalStatics.TEST_DATA_PROVIDER_WIKI, e);

            return false;
        }

        return true;
    }

    private boolean checkIfEqualsAndHashcodeReactToSetter(Class c, Object a, Object b, int iter, Method mySetter) {
        try {
            executeSetter(mySetter, a, iter);

            // Check same object
            if (!(areEqual(a, a))) {
                CheckerHelperFunctions.logFailedCheckerStep(LOGGER, mySetter,
                        "Object should be equal to it self (same pointer).");

                return true;
            }

            // Check different class. Guarantee to always use a different class
            // Nothing special about String, it's just not the Class Object
            if (areEqual(a, mySetter.getParameterTypes()[0].equals(Object.class) ? String.class : Object.class)) {
                CheckerHelperFunctions.logFailedCheckerStep(LOGGER, mySetter,
                        "Comparison with Object of different class should return false.");

                return true;
            }

            // Should not equal with different internal states
            if (areEqual(a, b) || haveEqualHashCode(c, a, b)) {
                CheckerHelperFunctions.logFailedCheckerStep(LOGGER, mySetter,
                        "Two objects with different states (one with setter invoked) should not equal or " +
                                "have equal hashCode.");

                return true;
            }

            // Set setter in second object
            executeSetter(mySetter, b, iter);

            // Should equal with same internal state
            if (!areEqual(a, b) || !haveEqualHashCode(c, a, b)) {
                CheckerHelperFunctions.logFailedCheckerStep(LOGGER, mySetter,
                        "Objects with same internal states should equal and have same hashCode");

                return true;
            }

            // increase seed


        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            CheckerHelperFunctions.logFailedCheckerStep(LOGGER, mySetter, e);
        }
        return false;
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
