package de.a9d3.testing.executer.exception;

import de.a9d3.testing.checker.CheckerInterface;

public class CheckerFailedException extends RuntimeException {
    public CheckerFailedException(CheckerInterface checker, Exception inner) {
        super("Checker " + checker.toString() + " failed. See inner exception.", inner);
    }

    public CheckerFailedException(CheckerInterface checker) {
        super("Checker " + checker.toString() + " failed.");
    }
}
