package de.a9d3.testing.checker;

import de.a9d3.testing.resource_classes.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HashcodeAndEqualsCheckTest {
    private CheckerInterface checker;

    @Before
    public void setup() {
        checker = new HashcodeAndEqualsCheck();
    }

    @Test
    public void positiveTest() {
        assertTrue(checker.check(HashCodeAndEqualsCheckPositiveTestClass.class));
    }

    @Test
    public void negativeFirstTest() {
        assertFalse(checker.check(HashCodeAndEqualsCheckNegativeFirstTestClass.class));
    }

    @Test
    public void negativeSecondTest() {
        assertFalse(checker.check(HashCodeAndEqualsCheckNegativeSecondTestClass.class));
    }

    @Test
    public void booleanValueTest() {
        // previously boolean values could inflict a failed test
        assertTrue(checker.check(HashCodeAndEqualsCheckBooleanTestClass.class));
    }

    @Test
    public void sameObjectTest() {
        assertFalse(checker.check(HashCodeAndEqualsCheckSameObjectTestClass.class));
    }

    @Test
    public void differentClassTest() {
        assertFalse(checker.check(HashCodeAndEqualsCheckDifferentClassTestClass.class));
    }
}
