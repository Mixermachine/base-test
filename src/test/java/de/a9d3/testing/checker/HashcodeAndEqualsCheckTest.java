package de.a9d3.testing.checker;

import de.a9d3.testing.resource_classes.HashCodeAndEqualsCheckNegativeFirstTestClass;
import de.a9d3.testing.resource_classes.HashCodeAndEqualsCheckNegativeSecondTestClass;
import de.a9d3.testing.resource_classes.HashCodeAndEqualsCheckPositiveTestClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HashcodeAndEqualsCheckTest {
    CheckerInterface checker;

    @Before
    public void setup() {
        checker = new HashcodeAndEqualsCheck();
    }

    @Test
    public void positiveTest() throws Exception {
        assertTrue(checker.check(HashCodeAndEqualsCheckPositiveTestClass.class));
    }

    @Test
    public void negativeFirstTest() throws Exception {
        assertFalse(checker.check(HashCodeAndEqualsCheckNegativeFirstTestClass.class));
    }

    @Test
    public void negativeSecondTest() throws Exception {
        assertFalse(checker.check(HashCodeAndEqualsCheckNegativeSecondTestClass.class));
    }

}
