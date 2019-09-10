package de.a9d3.testing.executer.exception;

import de.a9d3.testing.checker.CheckerInterface;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class CheckerFailedExceptionTest {

    @Test
    public void constructorTest() {
        CheckerInterface checker = c -> false;

        // should not throw exception
        checkSanity(new CheckerFailedException(checker));
        checkSanity(new CheckerFailedException(checker, new Exception()));
    }

    private void checkSanity(CheckerFailedException ex) {
        assertNotNull(ex.getMessage());
        assertNotNull(ex.toString());
    }
}