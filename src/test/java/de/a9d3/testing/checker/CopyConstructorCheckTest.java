package de.a9d3.testing.checker;

import de.a9d3.testing.resource_classes.CopyConstructorCheckNegativeTestClass;
import de.a9d3.testing.resource_classes.CopyConstructorCheckPositiveTestClass;
import de.a9d3.testing.resource_classes.CopyConstructorCheckWithoutEqualsAndHashcodeTestClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CopyConstructorCheckTest {

    private CheckerInterface checker;

    @Before
    public void setup() {
        checker = new CopyConstructorCheck();
    }

    @Test
    public void positiveTest() {
        assertTrue(checker.check(CopyConstructorCheckPositiveTestClass.class));
    }

    @Test
    public void withoutEqualsAndHashcode() {
        assertFalse(checker.check(CopyConstructorCheckWithoutEqualsAndHashcodeTestClass.class));

        checker = new CopyConstructorCheck(true);
        assertTrue(checker.check(CopyConstructorCheckWithoutEqualsAndHashcodeTestClass.class));
    }

    @Test
    public void negativeTest() {
        assertFalse(checker.check(CopyConstructorCheckNegativeTestClass.class));
    }
}