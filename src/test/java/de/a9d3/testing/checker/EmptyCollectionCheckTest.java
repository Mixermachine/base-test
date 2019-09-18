package de.a9d3.testing.checker;

import de.a9d3.testing.resource_classes.EmptyCollectionNegativeTestClass;
import de.a9d3.testing.resource_classes.EmptyCollectionPositiveTestClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmptyCollectionCheckTest {
    private CheckerInterface checker;

    @Before
    public void setup() {
        checker = new EmptyCollectionCheck();
    }

    @Test
    public void positiveTest() {
        assertTrue(checker.check(EmptyCollectionPositiveTestClass.class));
    }

    @Test
    public void negativeTest() {
        assertFalse(checker.check(EmptyCollectionNegativeTestClass.class));
    }
}
