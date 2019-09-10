package de.a9d3.testing.executer;

import de.a9d3.testing.checker.CheckerInterface;
import de.a9d3.testing.executer.exception.CheckerFailedException;

import java.util.List;

public class SingleThreadExecutor implements Executor {
    public Boolean execute(Class c, List<CheckerInterface> checkers) {
        for (CheckerInterface checker : checkers) {
            try {
                if (!checker.check(c)) {
                    throw new CheckerFailedException(checker);
                }
            } catch (ReflectiveOperationException e) {
                throw new CheckerFailedException(checker, e);
            }
        }

        return true;
    }
}
