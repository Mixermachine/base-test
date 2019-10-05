package de.a9d3.testing.checks;

import de.a9d3.testing.resource_classes.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PublicVariableCheckTest {

    private CheckInterface check;

    @Before
    public void setup() {
        check = new PublicVariableCheck();
    }

    @Test
    public void positiveTest() {
        assertTrue(check.check(PublicVariableCheckPositiveTestClass.class));

        check = new PublicVariableCheck(true);
        assertTrue(check.check(PublicVariableCheckPositiveTestClass.class));
    }

    @Test
    public void negativeTest() {
        assertFalse(check.check(PublicVariableCheckNegativeTestClass.class));

        check = new PublicVariableCheck(true);
        assertFalse(check.check(PublicVariableCheckNegativeTestClass.class));
    }

    @Test
    public void staticVariablesPositiveTest() {
        // standard check should reject
        assertFalse(check.check(PublicVariableCheckStaticVariablesPositiveTestClass.class));

        // special check should accept
        check = new PublicVariableCheck(true);
        assertTrue(check.check(PublicVariableCheckStaticVariablesPositiveTestClass.class));
    }

    @Test
    public void staticVariablesNegativeTest() {
        assertFalse(check.check(PublicVariableCheckStaticVariablesNegative0TestClass.class));
        assertFalse(check.check(PublicVariableCheckStaticVariablesNegative1TestClass.class));

        check = new PublicVariableCheck(true);
        assertFalse(check.check(PublicVariableCheckStaticVariablesNegative0TestClass.class));
        assertFalse(check.check(PublicVariableCheckStaticVariablesNegative1TestClass.class));
    }

}
