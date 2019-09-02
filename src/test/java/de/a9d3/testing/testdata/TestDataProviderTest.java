package de.a9d3.testing.testdata;


import de.a9d3.testing.resource_classes.TestDataProviderTestClass;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class TestDataProviderTest {

    private static final int MANY_ITERATIONS_QUANTITY = 1000;

    private TestDataProvider provider;

    @Before
    public void setup() {
        provider = new TestDataProvider();
    }

    @Test
    public void booleanTest() throws IllegalAccessException, InstantiationException {
        boolean pBool = provider.fill(boolean.class, "123", false); // should be false
        assertNotNull(pBool);

        assertEquals(pBool, provider.fill(boolean.class, "123", false)); // idempotency

        boolean pBoolSec = provider.fill(boolean.class, "12", false); // should be true
        assertNotEquals(pBool, pBoolSec);

        Boolean npBool = provider.fill(Boolean.class, "345", false);
        assertNotNull(npBool);

        assertEquals(pBool, provider.fill(Boolean.class, "345", false)); // idempotency

        Boolean npBoolSec = provider.fill(Boolean.class, "34", false); // should be false
        assertNotEquals(npBool, npBoolSec);

        testManyIfNotNull(boolean.class);
        testManyIfNotNull(Boolean.class);
    }

    @Test
    public void charTest() throws IllegalAccessException, InstantiationException {
        provider.fill(char.class, "123", false); // should not throw

        Character npChar = provider.fill(Character.class, "123", false);
        assertNotNull(npChar);

        testManyIfNotNull(char.class);
        testManyIfNotNull(Character.class);
    }

    @Test
    public void byteTest() throws IllegalAccessException, InstantiationException {
        provider.fill(byte.class, "123", false); // should not throw

        Byte npByte = provider.fill(Byte.class, "123", false);
        assertNotNull(npByte);

        testManyIfNotNull(byte.class);
        testManyIfNotNull(Byte.class);
    }

    @Test
    public void shortTest() throws IllegalAccessException, InstantiationException {
        provider.fill(short.class, "123", false); // should not throw

        Short npShort = provider.fill(Short.class, "123", false);
        assertNotNull(npShort);

        testManyIfNotNull(short.class);
        testManyIfNotNull(Short.class);
    }

    @Test
    public void intTest() throws IllegalAccessException, InstantiationException {
        provider.fill(int.class, "123", false);

        Integer npInteger = provider.fill(Integer.class, "123", false);
        assertNotNull(npInteger);

        testManyIfNotNull(int.class);
        testManyIfNotNull(Integer.class);
    }

    @Test
    public void longTest() throws IllegalAccessException, InstantiationException {
        provider.fill(long.class, "123", false);

        Long npLong = provider.fill(Long.class, "123", false);
        assertNotNull(npLong);

        testManyIfNotNull(long.class);
        testManyIfNotNull(Long.class);
    }

    @Test
    public void floatTest() throws IllegalAccessException, InstantiationException {
        provider.fill(float.class, "123", false);

        Float npFloat = provider.fill(Float.class, "123", false);
        assertNotNull(npFloat);

        testManyIfNotNull(float.class);
        testManyIfNotNull(Float.class);
    }

    @Test
    public void doubleTest() throws IllegalAccessException, InstantiationException {
        provider.fill(double.class, "123", false);

        Double npDouble = provider.fill(Double.class, "123", false);
        assertNotNull(npDouble);

        testManyIfNotNull(double.class);
        testManyIfNotNull(Double.class);
    }

    private void testManyIfNotNull(Class c) {
        AtomicBoolean positive = new AtomicBoolean(true);

        IntStream.range(0, TestDataProviderTest.MANY_ITERATIONS_QUANTITY).forEach(integer -> {
            assertNotNull(provider.fill(c, integer + "", false));
        });

        assertTrue(positive.get());
    }

    @Test
    public void generateTestDataByClassListTest() throws InstantiationException, IllegalAccessException {
        List<String> stringList = new ArrayList<>();
        stringList = provider.generateTestDataByNonStandardClass(stringList.getClass(), "123", false);

        assertNotNull(stringList);
        assertEquals(2, stringList.size());
    }

    @Test
    public void generateTestDataByClassMapTest() throws InstantiationException, IllegalAccessException {
        HashMap map = new HashMap<>();
        map = provider.generateTestDataByNonStandardClass(map.getClass(), "123", false);

        assertNotNull(map);
        assertEquals(1, map.size());
    }

    @Test
    public void generateTestDataByClassVariousClassTest() throws IllegalAccessException, InstantiationException {
        // Using random existing class
        Thread instance = provider.fill(Thread.class, "123", false);
        assertNotNull(instance);

        StringBuilder buffer = provider.fill(StringBuilder.class, "123", false);
        assertNotNull(buffer);
    }

    @Test
    public void generateTestDataByClassSimpleTest() {
        // should use simple constructor
        TestDataProviderTestClass testClass = provider.fill(TestDataProviderTestClass.class, "123", false);

        // default value for primitive
        assertEquals(0, testClass.getA());

        // null for complex and wrapper
        assertNull(testClass.getB());
        assertNull(testClass.getC());
        assertNull(testClass.getD());
    }

    @Test
    public void generateTestDataByClassComplexTest() {
        // should use complex constructor
        TestDataProviderTestClass testClass = provider.fill(TestDataProviderTestClass.class, "123", true);

        // primitive a can be any value now

        // complex and wrapper should be nonNull
        assertNotNull(testClass.getB());
        assertNotNull(testClass.getC());
        assertNotNull(testClass.getD());
    }


}
