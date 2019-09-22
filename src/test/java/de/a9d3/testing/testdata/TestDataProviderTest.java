package de.a9d3.testing.testdata;


import de.a9d3.testing.resource_classes.TestDataProviderTestClass;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class TestDataProviderTest {

    private static final int MANY_ITERATIONS_QUANTITY = 1000;

    private TestDataProvider provider;

    @Before
    public void setup() {
        provider = TestDataProvider.getSeededTestDataProvider();
    }

    @Test
    public void booleanTest() {
        boolean pBool = provider.fill(boolean.class, "123", false); // should be false

        assertEquals(pBool, provider.fill(boolean.class, "123", false)); // idempotence

        boolean pBoolSec = provider.fill(boolean.class, "12", false); // should be true
        assertNotEquals(pBool, pBoolSec);

        Boolean npBool = provider.fill(Boolean.class, "345", false);
        assertNotNull(npBool);

        assertEquals(pBool, provider.fill(Boolean.class, "345", false)); // idempotence

        Boolean npBoolSec = provider.fill(Boolean.class, "34", false); // should be false
        assertNotEquals(npBool, npBoolSec);

        testManyIfNotNull(boolean.class);
        testManyIfNotNull(Boolean.class);
    }

    @Test
    public void charTest() {
        provider.fill(char.class, "123", false); // should not throw

        Character npChar = provider.fill(Character.class, "123", false);
        assertNotNull(npChar);

        testManyIfNotNull(char.class);
        testManyIfNotNull(Character.class);
    }

    @Test
    public void byteTest() {
        provider.fill(byte.class, "123", false); // should not throw

        Byte npByte = provider.fill(Byte.class, "123", false);
        assertNotNull(npByte);

        testManyIfNotNull(byte.class);
        testManyIfNotNull(Byte.class);
    }

    @Test
    public void shortTest() {
        provider.fill(short.class, "123", false); // should not throw

        Short npShort = provider.fill(Short.class, "123", false);
        assertNotNull(npShort);

        testManyIfNotNull(short.class);
        testManyIfNotNull(Short.class);
    }

    @Test
    public void intTest() {
        provider.fill(int.class, "123", false);

        Integer npInteger = provider.fill(Integer.class, "123", false);
        assertNotNull(npInteger);

        testManyIfNotNull(int.class);
        testManyIfNotNull(Integer.class);
    }

    @Test
    public void longTest() {
        provider.fill(long.class, "123", false);

        Long npLong = provider.fill(Long.class, "123", false);
        assertNotNull(npLong);

        testManyIfNotNull(long.class);
        testManyIfNotNull(Long.class);
    }

    @Test
    public void floatTest() {
        provider.fill(float.class, "123", false);

        Float npFloat = provider.fill(Float.class, "123", false);
        assertNotNull(npFloat);

        testManyIfNotNull(float.class);
        testManyIfNotNull(Float.class);
    }

    @Test
    public void doubleTest() {
        provider.fill(double.class, "123", false);

        Double npDouble = provider.fill(Double.class, "123", false);
        assertNotNull(npDouble);

        testManyIfNotNull(double.class);
        testManyIfNotNull(Double.class);
    }

    @Test
    public void instantTest() {
        Instant instant = provider.fill(Instant.class, "123", false);

        assertNotNull(instant);
    }

    private void testManyIfNotNull(Class c) {
        AtomicBoolean positive = new AtomicBoolean(true);

        IntStream.range(0, TestDataProviderTest.MANY_ITERATIONS_QUANTITY).forEach(integer ->
                assertNotNull(provider.fill(c, integer + "", false)));

        assertTrue(positive.get());
    }

    @Test
    public void generateTestDataByClassListTest() {
        List<String> stringList = new ArrayList<>();
        stringList = provider.generateTestDataByNonStandardClass(stringList.getClass(), "123", false);

        assertNotNull(stringList);
        assertEquals(TestDataProvider.LIST_ARRAY_ITEM_COUNT, stringList.size());
    }

    @Test
    public void generateTestDataByClassMapTest() {
        HashMap map = new HashMap<>();
        map = provider.generateTestDataByNonStandardClass(map.getClass(), "123", false);

        assertNotNull(map);
        assertEquals(1, map.size());
    }

    @Test
    public void byteArrayTest() {
        Byte[] bytes = new Byte[0];
        bytes = provider.fill(bytes.getClass(), "123", false);

        assertNotNull(bytes);
        assertEquals(TestDataProvider.LIST_ARRAY_ITEM_COUNT, bytes.length);
    }

    @Test
    public void intArrayTest() {
        int[] ints = new int[0];
        ints = provider.fill(ints.getClass(), "123", false);

        assertNotNull(ints);
    }

    @Test
    public void generateTestDataByClassVariousClassTest() {
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

    @Test
    public void defaultTestDataProviderTest() {
        provider = TestDataProvider.getDefaultTestDataProvider();

        // check if returned values are defaults
        assertEquals(false, provider.fill(boolean.class, "123", false));
        assertEquals(0, (char) provider.fill(char.class, "123", false));
        assertEquals(0, (byte) provider.fill(byte.class, "123", false));
        assertEquals(0, (short) provider.fill(short.class, "123", false));
        assertEquals(0, (int) provider.fill(int.class, "123", false));
        assertEquals(0, (long) provider.fill(long.class, "123", false));
        assertEquals(0, provider.fill(float.class, "123", false), 0);
        assertEquals(0.0, provider.fill(double.class, "123", false), 0);

        assertEquals(false, provider.fill(Boolean.class, "123", false));
        assertEquals(0, (char) provider.fill(Character.class, "123", false));
        assertEquals(0, (byte) provider.fill(Byte.class, "123", false));
        assertEquals(0, (short) provider.fill(Short.class, "123", false));
        assertEquals(0, (int) provider.fill(Integer.class, "123", false));
        assertEquals(0, (long) provider.fill(Long.class, "123", false));
        assertEquals(0, provider.fill(Float.class, "123", false), 0);
        assertEquals(0.0, provider.fill(Double.class, "123", false), 0);

        assertEquals("", provider.fill(String.class, "123", false));
        assertEquals(Instant.ofEpochSecond(0), provider.fill(Instant.class, "123", false));
    }

    @Test
    public void addCustomMappingsTest() {
        Character checkChar = 'c';

        provider = new TestDataProvider(Collections.emptyMap());
        assertNull(provider.fill(Character.class, "123", false));

        Map<String, Function<String, Object>> map = new HashMap<>();
        map.put(Character.class.getName(), s -> checkChar);

        provider.addCustomMappings(map);

        assertEquals(checkChar, provider.fill(Character.class, "123", false));
    }
}
