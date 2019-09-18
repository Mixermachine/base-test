package de.a9d3.testing.checker.exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MismatchExceptionTest {

    @Test
    public void instantiationTest() {
        String message = "db659c95-4ce5-47ae-b511-19f411d7bde5";

        Exception exception = new MismatchException(message);

        assertEquals(message, exception.getMessage());
    }
}