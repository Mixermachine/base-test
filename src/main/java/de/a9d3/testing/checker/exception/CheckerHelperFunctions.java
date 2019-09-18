package de.a9d3.testing.checker.exception;

import java.util.logging.Logger;

public final class CheckerHelperFunctions {
    private CheckerHelperFunctions() {
        // should not be initialized
    }

    public static void logFailedCheckerStep(Logger logger, Object testedObject) {
        logFailedCheckerStep(logger, testedObject, null, null);
    }

    public static void logFailedCheckerStep(Logger logger, Object testedObject, String message) {
        logFailedCheckerStep(logger, testedObject, message, null);
    }

    public static void logFailedCheckerStep(Logger logger, Object testedObject, Exception exception) {
        logFailedCheckerStep(logger, testedObject, null, exception);
    }

    public static void logFailedCheckerStep(Logger logger, Object testedObject, String message,
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
}
