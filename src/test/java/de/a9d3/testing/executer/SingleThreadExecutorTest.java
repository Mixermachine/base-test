package de.a9d3.testing.executer;

import de.a9d3.testing.checker.CheckerInterface;
import de.a9d3.testing.executer.exception.CheckerFailedException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class SingleThreadExecutorTest {

    private Executor executor;

    @Before
    public void setup() {
        executor = new SingleThreadExecutor();
    }

    @Test
    public void positiveTest() {
        CheckerInterface checker1 = c -> true;

        CheckerInterface checker2 = c -> true;

        assertTrue(executor.execute(Object.class, Arrays.asList(checker1, checker2)));
    }

    @Test(expected = CheckerFailedException.class)
    public void negativeTest() {
        CheckerInterface checker1 = c -> true;

        CheckerInterface checker2 = c -> false;

        executor.execute(Object.class, Arrays.asList(checker1, checker2));
    }
}