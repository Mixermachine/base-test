package de.a9d3.testing.checker;

import de.a9d3.testing.resource_classes.EmptyCollectionNegativeTestClass;
import de.a9d3.testing.resource_classes.EmptyCollectionPositiveTestClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmptyCollectionCheckTest {
    private CheckerInterface checker;

    @Before
    public void setup() {
        checker = new EmptyCollectionCheck();
    }

    @Test
    public void positiveTest() throws Exception {
        assertTrue(checker.check(EmptyCollectionPositiveTestClass.class));
    }

    @Test
    public void negativeTest() throws Exception {
        assertFalse(checker.check(EmptyCollectionNegativeTestClass.class));
    }
}
