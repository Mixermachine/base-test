package de.a9d3.testing.executer;

import de.a9d3.testing.checks.CheckInterface;
import de.a9d3.testing.executer.exception.CheckFailedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SingleThreadExecutor implements Executor {
    private static final Logger LOGGER = Logger.getLogger(SingleThreadExecutor.class.getName());

    private static String executionLogToString(Class testClass, Map<String, String> executionLog) {
        StringBuilder builder = new StringBuilder();
        builder.append("Tested ");
        builder.append(testClass.getName());
        builder.append("\n");

        executionLog.forEach((check, log) -> {
            builder.append(check);
            builder.append(": ");
            builder.append(log);
            builder.append("\n");
        });

        return builder.toString();
    }

    public Boolean execute(Class c, List<CheckInterface> checks) {
        Map<String, String> executionLog = new HashMap<>();
        boolean failed = false;

        for (CheckInterface check : checks) {
            boolean result = check.check(c);
            executionLog.put(check.getClass().getName(), result ?
                    "Passed ✔️" : "Failed ❌");

            if (!result) {
                failed = true;
            }
        }

        if (failed) {
            throw new CheckFailedException(executionLogToString(c, executionLog));
        }

        LOGGER.info(() -> executionLogToString(c, executionLog));
        return true;
    }
}
