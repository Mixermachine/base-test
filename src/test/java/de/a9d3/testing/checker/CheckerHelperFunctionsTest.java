package de.a9d3.testing.checker;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CheckerHelperFunctionsTest {

    private Logger logger;

    private List<LogRecord> logs;

    @Before
    public void setup() {
        logger = Logger.getLogger(CheckerHelperFunctions.class.getName());
        logs = new ArrayList<>();

        logger.addHandler(new Handler() {
            @Override
            public void publish(LogRecord logRecord) {
                logs.add(logRecord);
            }

            @Override
            public void flush() {
            }

            @Override
            public void close() throws SecurityException {
            }
        });
    }

    @Test
    public void logFailedCheckerStepSimpleTest() {
        String testObject = "dc83f192-6cfe-4036-8f3d-089e6a9982a4";

        CheckerHelperFunctions.logFailedCheckerStep(logger, testObject);

        assertEquals(1, logs.size());
        assertTrue(logs.get(0).getMessage().contains(testObject));
    }

    @Test
    public void logFailedCheckerStepWithMessageTest() {
        String testObject = "cb7dcc36-c33e-430b-8262-fe5621661057";
        String message = "657c3cd6-1e74-4ced-ba95-d42e85ee86e7";

        CheckerHelperFunctions.logFailedCheckerStep(logger, testObject, message);

        assertEquals(1, logs.size());
        assertTrue(logs.get(0).getMessage().contains(testObject));
        assertTrue(logs.get(0).getMessage().contains(message));
    }

    @Test
    public void logFailedCheckerStepWithExceptionTest() {
        String testObject = "0a8ff8d0-c12a-4eb1-a9f2-3ed7b0d10490";
        Exception exception = new Exception("ccebd590-7e73-46fb-a09b-bff3d7af28a2");

        CheckerHelperFunctions.logFailedCheckerStep(logger, testObject, exception);

        assertEquals(1, logs.size());
        assertTrue(logs.get(0).getMessage().contains(testObject));
        assertTrue(logs.get(0).getMessage().contains(exception.getMessage()));
    }

    @Test
    public void logFailedCheckerStepWithMessageAndExceptionTest() {
        String testObject = "6a296da3-0476-4f26-8975-dd33c7a89be1";
        String message = "3fd31f7a-51c1-48ef-9e75-0a98cbec0726";
        Exception exception = new Exception("30b071bb-ac62-4932-b310-4ea10324ea95");

        CheckerHelperFunctions.logFailedCheckerStep(logger, testObject, message, exception);

        assertEquals(1, logs.size());
        assertTrue(logs.get(0).getMessage().contains(testObject));
        assertTrue(logs.get(0).getMessage().contains(message));
        assertTrue(logs.get(0).getMessage().contains(exception.getMessage()));
    }
}