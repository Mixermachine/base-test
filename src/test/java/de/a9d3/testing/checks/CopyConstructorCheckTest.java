package de.a9d3.testing.checks;

import de.a9d3.testing.resource_classes.CopyConstructorCheckNegativeTestClass;
import de.a9d3.testing.resource_classes.CopyConstructorCheckPositiveTestClass;
import de.a9d3.testing.resource_classes.CopyConstructorCheckWithoutEqualsAndHashcodeTestClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CopyConstructorCheckTest {

    private CheckInterface check;

    @Before
    public void setup() {
        check = new CopyConstructorCheck();
    }

    @Test
    public void positiveTest() {
        assertTrue(check.check(CopyConstructorCheckPositiveTestClass.class));
    }

    @Test
    public void withoutEqualsAndHashcode() {
        assertFalse(check.check(CopyConstructorCheckWithoutEqualsAndHashcodeTestClass.class));

        check = new CopyConstructorCheck(true);
        assertTrue(check.check(CopyConstructorCheckWithoutEqualsAndHashcodeTestClass.class));
    }

    @Test
    public void negativeTest() {
        assertFalse(check.check(CopyConstructorCheckNegativeTestClass.class));
    }

    @Test
    public void noCopyConstructorTest() {
        // this test does not have a copyConstructor. Should instantly return false.
        assertFalse(check.check(CopyConstructorCheckTest.class));

        // even without provider (also increases test coverage)
        check = new CopyConstructorCheck(null);
        assertFalse(check.check(CopyConstructorCheckTest.class));
    }
}