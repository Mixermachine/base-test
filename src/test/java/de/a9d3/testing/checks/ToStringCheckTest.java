package de.a9d3.testing.checks;

import de.a9d3.testing.resource_classes.ToStringCheckNegativeClass;
import de.a9d3.testing.resource_classes.ToStringCheckPositiveClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ToStringCheckTest {

    CheckInterface check;

    @Before
    public void setUp() {
        check = new ToStringCheck();
    }

    @Test
    public void positiveTest() {
        assertTrue(check.check(ToStringCheckPositiveClass.class));
    }

    @Test
    public void positiveExclusionsTest() {
        // excluding d fields which is not in toString method()
        check = new ToStringCheck("d");
        assertTrue(check.check(ToStringCheckNegativeClass.class));
    }

    @Test
    public void negativeTest() {
        assertFalse(check.check(ToStringCheckNegativeClass.class));
    }
}