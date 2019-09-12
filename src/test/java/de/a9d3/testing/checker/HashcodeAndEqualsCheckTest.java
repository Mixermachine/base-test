package de.a9d3.testing.checker;

import de.a9d3.testing.resource_classes.HashCodeAndEqualsCheckBooleanTestClass;
import de.a9d3.testing.resource_classes.HashCodeAndEqualsCheckNegativeFirstTestClass;
import de.a9d3.testing.resource_classes.HashCodeAndEqualsCheckNegativeSecondTestClass;
import de.a9d3.testing.resource_classes.HashCodeAndEqualsCheckPositiveTestClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HashcodeAndEqualsCheckTest {
    CheckerInterface checker;

    @Before
    public void setup() {
        checker = new HashcodeAndEqualsCheck();
    }

    @Test
    public void positiveTest() throws ReflectiveOperationException {
        assertTrue(checker.check(HashCodeAndEqualsCheckPositiveTestClass.class));
    }

    @Test
    public void negativeFirstTest() throws ReflectiveOperationException {
        assertFalse(checker.check(HashCodeAndEqualsCheckNegativeFirstTestClass.class));
    }

    @Test
    public void negativeSecondTest() throws ReflectiveOperationException {
        assertFalse(checker.check(HashCodeAndEqualsCheckNegativeSecondTestClass.class));
    }

    @Test
    public void booleanValueTest() throws ReflectiveOperationException {
        // previously boolean values could not inflict a failed test
        assertTrue(checker.check(HashCodeAndEqualsCheckBooleanTestClass.class));
    }

}
