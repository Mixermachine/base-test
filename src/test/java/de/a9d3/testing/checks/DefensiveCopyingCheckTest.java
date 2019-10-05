package de.a9d3.testing.checks;

import de.a9d3.testing.resource_classes.DefensiveCopyingCheckMutableTestClass;
import de.a9d3.testing.resource_classes.DefensiveCopyingCheckOnlyImmutableTestClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DefensiveCopyingCheckTest {

    CheckInterface check;

    @Before
    public void setup() {
        check = new DefensiveCopyingCheck();
    }

    @Test
    public void positiveOnlyImmutableTest() {
        assertTrue(check.check(DefensiveCopyingCheckOnlyImmutableTestClass.class));
    }

    @Test
    public void positiveMutable() {
        assertTrue(check.check(DefensiveCopyingCheckMutableTestClass.class));
    }
}
