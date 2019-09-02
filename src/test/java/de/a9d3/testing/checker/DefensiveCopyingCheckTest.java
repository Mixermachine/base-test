package de.a9d3.testing.checker;

import de.a9d3.testing.checker.exception.MismatchException;
import de.a9d3.testing.resource_classes.DefensiveCopyingCheckMutableTestClass;
import de.a9d3.testing.resource_classes.DefensiveCopyingCheckOnlyImmutableTestClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefensiveCopyingCheckTest {

    CheckerInterface checker;

    @Before
    public void setup() {
        checker = new DefensiveCopyingCheck();
    }

    @Test
    public void positiveOnlyImmutableTest() throws MismatchException, ReflectiveOperationException {
        assertTrue(checker.check(DefensiveCopyingCheckOnlyImmutableTestClass.class));
    }

    @Test
    public void positiveMutable() throws MismatchException, ReflectiveOperationException {
        assertTrue(checker.check(DefensiveCopyingCheckMutableTestClass.class));
    }
}
