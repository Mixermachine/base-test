package de.a9d3.testing.method;

import de.a9d3.testing.resource_classes.TestClass;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class GetterSetterMatcherTest {
    MethodMatcherInterface matcher;

    @Before
    public void setup() {
        matcher = new GetterSetterMatcher();
    }

    @Test
    public void matchPositiveTest() throws NoSuchMethodException {
        List<MethodTuple> result = matcher.match(TestClass.class);

        assertNotNull(result);
        assertEquals(4, result.size());

        Class tc = TestClass.class;

        List<MethodTuple> expected = Arrays.asList(
                new MethodTuple(tc.getMethod("getB"), tc.getMethod("setB", Boolean.class)),
                new MethodTuple(tc.getMethod("getC"), tc.getMethod("setC", String.class)),
                new MethodTuple(tc.getMethod("getD"), tc.getMethod("setD", int.class)),
                new MethodTuple(tc.getMethod("getE"), tc.getMethod("setE", Integer.class)));

        assertEquals(expected.size(), result.size());
        assertTrue(result.containsAll(expected));
    }
}
