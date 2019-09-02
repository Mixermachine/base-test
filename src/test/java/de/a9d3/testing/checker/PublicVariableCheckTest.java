package de.a9d3.testing.checker;

import de.a9d3.testing.checker.exception.MismatchException;
import de.a9d3.testing.resource_classes.PublicVariableCheckNegativeTestClass;
import de.a9d3.testing.resource_classes.PublicVariableCheckPositiveTestClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PublicVariableCheckTest {

    private CheckerInterface checker;

    @Before
    public void setup() {
        checker = new PublicVariableCheck();
    }

    @Test
    public void positiveTest() throws MismatchException, ReflectiveOperationException {
        assertTrue(checker.check(PublicVariableCheckPositiveTestClass.class));
    }

    @Test
    public void negativeTest() throws MismatchException, ReflectiveOperationException {
        assertFalse(checker.check(PublicVariableCheckNegativeTestClass.class));
    }

}
