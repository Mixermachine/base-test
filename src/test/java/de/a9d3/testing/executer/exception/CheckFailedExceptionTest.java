package de.a9d3.testing.executer.exception;

import de.a9d3.testing.checks.CheckInterface;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class CheckFailedExceptionTest {

    @Test
    public void constructorTest() {
        CheckInterface check = c -> false;

        // should not throw exception
        checkSanity(new CheckFailedException(check));
        checkSanity(new CheckFailedException(check, new Exception()));
    }

    private void checkSanity(CheckFailedException ex) {
        assertNotNull(ex.getMessage());
        assertNotNull(ex.toString());
    }
}