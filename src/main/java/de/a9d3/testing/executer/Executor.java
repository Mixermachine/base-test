package de.a9d3.testing.executer;

import de.a9d3.testing.checks.CheckInterface;

import java.util.List;

public interface Executor {
    Boolean execute(Class c, List<CheckInterface> checks);
}
