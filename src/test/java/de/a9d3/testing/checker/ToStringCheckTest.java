package de.a9d3.testing.checker;

import de.a9d3.testing.resource_classes.ToStringCheckNegativeClass;
import de.a9d3.testing.resource_classes.ToStringCheckPositiveClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ToStringCheckTest {

    CheckerInterface check;

    @Before
    public void setUp() {
        check = new ToStringCheck();
    }

    @Test
    public void positiveTest() throws ReflectiveOperationException {
        assertTrue(check.check(ToStringCheckPositiveClass.class));
    }

    @Test
    public void negativeTest() throws ReflectiveOperationException {
        assertFalse(check.check(ToStringCheckNegativeClass.class));
    }
}