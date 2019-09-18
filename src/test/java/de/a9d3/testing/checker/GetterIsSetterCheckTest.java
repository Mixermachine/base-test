package de.a9d3.testing.checker;

import de.a9d3.testing.resource_classes.GetterIsSetterCheckNegativeTestClass;
import de.a9d3.testing.resource_classes.GetterIsSetterCheckPositiveTestClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GetterIsSetterCheckTest {

    CheckerInterface check;

    @Before
    public void setup() {
        check = new GetterIsSetterCheck();
    }

    @Test
    public void positiveTest() {
        assertTrue(check.check(GetterIsSetterCheckPositiveTestClass.class));
    }

    @Test
    public void negativeTest() {
        assertFalse(check.check(GetterIsSetterCheckNegativeTestClass.class));
    }
}
