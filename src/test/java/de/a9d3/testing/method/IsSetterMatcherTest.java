package de.a9d3.testing.method;

import de.a9d3.testing.resource_classes.TestClass;
import de.a9d3.testing.tuple.MethodTuple;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IsSetterMatcherTest {

    MethodMatcherInterface matcher;

    @Before
    public void setup() {
        matcher = new IsSetterMatcher();
    }

    @Test
    public void matchPositiveTest() throws NoSuchMethodException {
        List<MethodTuple> result = matcher.match(TestClass.class);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(TestClass.class.getMethod("isA"), result.get(0).getA());
        assertEquals(TestClass.class.getMethod("setA", boolean.class), result.get(0).getB());
    }
}
