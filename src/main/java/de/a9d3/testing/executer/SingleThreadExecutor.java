package de.a9d3.testing.executer;

import de.a9d3.testing.checker.CheckerInterface;
import de.a9d3.testing.checker.exception.MismatchException;
import de.a9d3.testing.executer.exception.CheckerFailedException;

import java.util.List;

public class SingleThreadExecutor {
    public Boolean execute(Class c, List<CheckerInterface> checkers) {
        for (CheckerInterface checker : checkers) {
            try {
                checker.check(c);
            } catch (ReflectiveOperationException | MismatchException e) {
                throw new CheckerFailedException(checker, e);
            }
        }

        return true;
    }
}
