package de.a9d3.testing.executer.exception;

import de.a9d3.testing.checks.CheckInterface;

public class CheckFailedException extends RuntimeException {
    public CheckFailedException(CheckInterface check, Exception inner) {
        super("check " + check.toString() + " failed. See inner exception.", inner);
    }

    public CheckFailedException(CheckInterface check) {
        super("check " + check.toString() + " failed.");
    }

    public CheckFailedException(String reason) {
        super(reason);
    }
}
