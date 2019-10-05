package de.a9d3.testing.checks;

import de.a9d3.testing.resource_classes.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HashcodeAndEqualsCheckTest {
    private CheckInterface check;

    @Before
    public void setup() {
        check = new HashcodeAndEqualsCheck();
    }

    @Test
    public void positiveTest() {
        assertTrue(check.check(HashCodeAndEqualsCheckPositiveTestClass.class));
    }

    @Test
    public void negativeFirstTest() {
        assertFalse(check.check(HashCodeAndEqualsCheckNegativeFirstTestClass.class));
    }

    @Test
    public void negativeSecondTest() {
        assertFalse(check.check(HashCodeAndEqualsCheckNegativeSecondTestClass.class));
    }

    @Test
    public void booleanValueTest() {
        // previously boolean values could inflict a failed test
        assertTrue(check.check(HashCodeAndEqualsCheckBooleanTestClass.class));
    }

    @Test
    public void sameObjectTest() {
        assertFalse(check.check(HashCodeAndEqualsCheckSameObjectTestClass.class));
    }

    @Test
    public void differentClassTest() {
        assertFalse(check.check(HashCodeAndEqualsCheckDifferentClassTestClass.class));
    }
}
