package de.a9d3.testing.checks;

import de.a9d3.testing.resource_classes.GetterIsSetterCheckNegativeTestClass;
import de.a9d3.testing.resource_classes.GetterIsSetterCheckPositiveTestClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GetterIsSetterCheckTest {

    CheckInterface check;

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

    @Test
    public void regexPositiveMatchTest() {
        // Exclude faulty getter/setter
        check = new GetterIsSetterCheck("getD");
        assertTrue(check.check(GetterIsSetterCheckNegativeTestClass.class));
    }

    @Test
    public void regexNegativeMatchTest() {
        // Exclude something non faulty but still keep faulty getter/setter
        check = new GetterIsSetterCheck("getA");
        assertFalse(check.check(GetterIsSetterCheckNegativeTestClass.class));
    }

}
