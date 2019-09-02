package de.a9d3.testing.checker;

import de.a9d3.testing.checker.exception.MismatchException;
import de.a9d3.testing.resource_classes.GetterIsSetterCheckNegativeTestClass;
import de.a9d3.testing.resource_classes.GetterIsSetterCheckPositiveTestClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GetterIsSetterCheckTest {

    CheckerInterface check;

    @Before
    public void setup() {
        check = new GetterIsSetterCheck();
    }

    @Test
    public void positiveTest()
            throws ReflectiveOperationException, MismatchException {
        assertTrue(check.check(GetterIsSetterCheckPositiveTestClass.class));

    }

    @Test(expected = MismatchException.class)
    public void negativeTest() throws ReflectiveOperationException, MismatchException {
        check.check(GetterIsSetterCheckNegativeTestClass.class);
    }
}
