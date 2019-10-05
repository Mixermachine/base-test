package de.a9d3.testing.executer;

import de.a9d3.testing.checks.CheckInterface;
import de.a9d3.testing.executer.exception.CheckFailedException;
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
        CheckInterface check1 = c -> true;

        CheckInterface check2 = c -> true;

        assertTrue(executor.execute(Object.class, Arrays.asList(check1, check2)));
    }

    @Test(expected = CheckFailedException.class)
    public void negativeTest() {
        CheckInterface check1 = c -> true;

        CheckInterface check2 = c -> false;

        executor.execute(Object.class, Arrays.asList(check1, check2));
    }
}