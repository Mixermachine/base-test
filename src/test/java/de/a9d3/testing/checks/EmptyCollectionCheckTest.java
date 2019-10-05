package de.a9d3.testing.checks;

import de.a9d3.testing.resource_classes.EmptyCollectionNegativeTestClass;
import de.a9d3.testing.resource_classes.EmptyCollectionPositiveTestClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmptyCollectionCheckTest {
    private CheckInterface check;

    @Before
    public void setup() {
        check = new EmptyCollectionCheck();
    }

    @Test
    public void positiveTest() {
        assertTrue(check.check(EmptyCollectionPositiveTestClass.class));
    }

    @Test
    public void negativeTest() {
        assertFalse(check.check(EmptyCollectionNegativeTestClass.class));
    }
}
