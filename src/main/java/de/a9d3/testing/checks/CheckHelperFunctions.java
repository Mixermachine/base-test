package de.a9d3.testing.checks;

import de.a9d3.testing.testdata.TestDataProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public final class CheckHelperFunctions {
    private CheckHelperFunctions() {
        // should not be initialized
    }

    public static void logFailedCheckStep(Logger logger, Object testedObject) {
        logFailedCheckStep(logger, testedObject, null, null);
    }

    public static void logFailedCheckStep(Logger logger, Object testedObject, String message) {
        logFailedCheckStep(logger, testedObject, message, null);
    }

    public static void logFailedCheckStep(Logger logger, Object testedObject, Exception exception) {
        logFailedCheckStep(logger, testedObject, null, exception);
    }

    public static void logFailedCheckStep(Logger logger, Object testedObject, String message,
                                          Exception exception) {
        StringBuilder builder = new StringBuilder();
        builder.append("Check for ");
        builder.append(testedObject);
        builder.append(" failed.");

        if (message != null) {
            builder.append(" ");
            builder.append(message);
        }

        if (exception != null) {
            builder.append(" Received exception: ");
            builder.append(exception.getMessage());
        }

        logger.warning(builder::toString);
    }

    public static void executeSetter(TestDataProvider provider, Method m, Object o, int iter)
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
}
