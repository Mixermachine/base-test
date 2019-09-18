package de.a9d3.testing.executer;

import de.a9d3.testing.checker.CheckerInterface;
import de.a9d3.testing.executer.exception.CheckerFailedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SingleThreadExecutor implements Executor {
    private static final Logger LOGGER = Logger.getLogger(SingleThreadExecutor.class.getName());

    private static String executionLogToString(Map<String, String> executionLog) {
        StringBuilder builder = new StringBuilder();

        executionLog.forEach((checker, log) -> {
            builder.append(checker);
            builder.append(": ");
            builder.append(log);
            builder.append("\n");
        });

        return builder.toString();
    }

    public Boolean execute(Class c, List<CheckerInterface> checkers) {
        Map<String, String> executionLog = new HashMap<>();
        boolean failed = false;

        for (CheckerInterface checker : checkers) {
            boolean result = checker.check(c);
            executionLog.put(checker.getClass().getName(), result ?
                    "Passed ✔️" : "Failed ❌");

            if (!result) {
                failed = true;
            }
        }

        if (failed) {
            throw new CheckerFailedException(executionLogToString(executionLog));
        }

        LOGGER.info(() -> executionLogToString(executionLog));
        return true;
    }
}
