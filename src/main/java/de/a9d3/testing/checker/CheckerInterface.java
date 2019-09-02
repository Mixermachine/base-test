package de.a9d3.testing.checker;

import de.a9d3.testing.checker.exception.MismatchException;

public interface CheckerInterface {
    boolean check(Class c) throws ReflectiveOperationException, MismatchException;
}
