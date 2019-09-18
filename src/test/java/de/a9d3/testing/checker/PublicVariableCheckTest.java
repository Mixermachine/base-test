package de.a9d3.testing.checker;

import de.a9d3.testing.resource_classes.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PublicVariableCheckTest {

    private CheckerInterface checker;

    @Before
    public void setup() {
        checker = new PublicVariableCheck();
    }

    @Test
    public void positiveTest() {
        assertTrue(checker.check(PublicVariableCheckPositiveTestClass.class));

        checker = new PublicVariableCheck(true);
        assertTrue(checker.check(PublicVariableCheckPositiveTestClass.class));
    }

    @Test
    public void negativeTest() {
        assertFalse(checker.check(PublicVariableCheckNegativeTestClass.class));

        checker = new PublicVariableCheck(true);
        assertFalse(checker.check(PublicVariableCheckNegativeTestClass.class));
    }

    @Test
    public void staticVariablesPositiveTest() {
        // standard checker should reject
        assertFalse(checker.check(PublicVariableCheckStaticVariablesPositiveTestClass.class));

        // special checker should accept
        checker = new PublicVariableCheck(true);
        assertTrue(checker.check(PublicVariableCheckStaticVariablesPositiveTestClass.class));
    }

    @Test
    public void staticVariablesNegativeTest() {
        assertFalse(checker.check(PublicVariableCheckStaticVariablesNegative0TestClass.class));
        assertFalse(checker.check(PublicVariableCheckStaticVariablesNegative1TestClass.class));

        checker = new PublicVariableCheck(true);
        assertFalse(checker.check(PublicVariableCheckStaticVariablesNegative0TestClass.class));
        assertFalse(checker.check(PublicVariableCheckStaticVariablesNegative1TestClass.class));
    }

}
