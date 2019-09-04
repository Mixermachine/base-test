package de.a9d3.testing.executer;

import de.a9d3.testing.checker.CheckerInterface;

import java.util.List;

public interface Executor {
    Boolean execute(Class c, List<CheckerInterface> checkers);
}
