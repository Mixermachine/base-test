package de.a9d3.testing.checker;

import de.a9d3.testing.resource_classes.DefensiveCopyingCheckMutableTestClass;
import de.a9d3.testing.resource_classes.DefensiveCopyingCheckOnlyImmutableTestClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DefensiveCopyingCheckTest {

    CheckerInterface checker;

    @Before
    public void setup() {
        checker = new DefensiveCopyingCheck();
    }

    @Test
    public void positiveOnlyImmutableTest() throws ReflectiveOperationException {
        assertTrue(checker.check(DefensiveCopyingCheckOnlyImmutableTestClass.class));
    }

    @Test
    public void positiveMutable() throws ReflectiveOperationException {
        assertTrue(checker.check(DefensiveCopyingCheckMutableTestClass.class));
    }
}
