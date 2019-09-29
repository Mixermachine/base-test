package de.a9d3.testing.checker;

import de.a9d3.testing.method.GetterSetterMatcher;
import de.a9d3.testing.method.IsSetterMatcher;
import de.a9d3.testing.method.MethodMatcherInterface;
import de.a9d3.testing.testdata.TestDataProvider;
import de.a9d3.testing.tuple.MethodTuple;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class DefensiveCopyingCheck implements CheckerInterface {
    private static final Logger LOGGER = Logger.getLogger(DefensiveCopyingCheck.class.getName());

    private TestDataProvider provider;

    public DefensiveCopyingCheck() {
        this(new TestDataProvider());
    }

    public DefensiveCopyingCheck(TestDataProvider provider) {
        this.provider = provider;
    }

    private static boolean isPrimitiveWrapper(Class c) {
        return c == Double.class || c == Float.class || c == Long.class ||
                c == Integer.class || c == Short.class || c == Character.class ||
                c == Byte.class || c == Boolean.class;
    }

    private static boolean isString(Class c) {
        return c == String.class;
    }

    @Override
    public boolean check(Class c) {
        MethodMatcherInterface getterSetterMatcher = new GetterSetterMatcher();
        MethodMatcherInterface isSetterMatcher = new IsSetterMatcher();

        return Stream.concat(getterSetterMatcher.match(c).stream(), isSetterMatcher.match(c).stream())
                .noneMatch(tuple -> checkIfClassImplementsDefensiveCopyingForMethodTuple(c, tuple));
    }

    private boolean checkIfClassImplementsDefensiveCopyingForMethodTuple(Class c, MethodTuple tuple) {
        Class myC = tuple.getA().getReturnType();

        if (myC.isPrimitive() || isPrimitiveWrapper(myC) || isString(myC)) {
            return false;
        } else {
            try {
                return checkIfComplexClassImplementsDefensiveCopyingForMethodTuple(c, tuple);
            } catch (IllegalAccessException | InvocationTargetException e) {
                CheckerHelperFunctions.logFailedCheckerStep(LOGGER, tuple, e);
            }
        }
        return true;
    }

    private boolean checkIfComplexClassImplementsDefensiveCopyingForMethodTuple(Class c, MethodTuple tuple) throws IllegalAccessException, InvocationTargetException {
        // check if getter returns same item which has been previously set by
        Object a = provider.fill(c, "123", false);

        Object data = provider
                .fill(tuple.getA().getReturnType(), "824a5189-0014-4d64-a7bd-89f921ab8de2",
                        false);

        tuple.getB().invoke(a, data);

        boolean result = data == tuple.getA().invoke(a);

        if (result) {
            CheckerHelperFunctions.logFailedCheckerStep(LOGGER, tuple,
                    "Returned object is the same object which was previously set.");
        }

        return result;
    }
}
