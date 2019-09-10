package de.a9d3.testing.checker;

import de.a9d3.testing.method.GetterSetterMatcher;
import de.a9d3.testing.method.IsSetterMatcher;
import de.a9d3.testing.method.MethodMatcherInterface;
import de.a9d3.testing.testdata.TestDataProvider;

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

    @Override
    public boolean check(Class c)  {
        MethodMatcherInterface getterSetterMatcher = new GetterSetterMatcher();
        MethodMatcherInterface isSetterMatcher = new IsSetterMatcher();

        return Stream.concat(getterSetterMatcher.match(c).stream(), isSetterMatcher.match(c).stream())
                .noneMatch(tuple -> {
                    try {
                        Class myC = tuple.getA().getReturnType();
                        if (myC.isPrimitive() || isPrimitiveWrapper(myC) || isString(myC)) {
                            return false;
                        } else {
                            // check if getter returns same item which has been previously set by
                            Object a = provider.fill(c, "123", false);

                            Object data = provider
                                    .fill(tuple.getA().getReturnType(), "824a5189-0014-4d64-a7bd-89f921ab8de2",
                                            false);

                            tuple.getB().invoke(a, data);

                            return data == tuple.getA().invoke(a);
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        LOGGER.warning("Received following exception while checking tuple " + tuple.toString() +
                                ". Exception " + e.getMessage());
                    }

                    return true;
                });
    }

    private boolean isPrimitiveWrapper(Class c) {
        return c == Double.class || c == Float.class || c == Long.class ||
                c == Integer.class || c == Short.class || c == Character.class ||
                c == Byte.class || c == Boolean.class;
    }

    private boolean isString(Class c) {
        return c == String.class;
    }
}
