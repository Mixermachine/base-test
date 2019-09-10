package de.a9d3.testing.method_extractor;

import de.a9d3.testing.resource_classes.ExtractorTestClass;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GetterIsSetterExtractorTest {

    @Test
    public void getGetterTest() {
        List<Method> methods = GetterIsSetterExtractor.getGetter(ExtractorTestClass.class);

        assertNotNull(methods);
        assertEquals(5, methods.size()); // including getClass
    }

    @Test
    public void getIsTest() {
        List<Method> methods = GetterIsSetterExtractor.getIs(ExtractorTestClass.class);

        assertNotNull(methods);
        assertEquals(2, methods.size());
    }

    @Test
    public void getGetterAndIsTest() {
        List<Method> methods = GetterIsSetterExtractor.getGetterAndIs(ExtractorTestClass.class);

        assertNotNull(methods);
        assertEquals(7, methods.size());
    }

    @Test
    public void getSetterTest() {
        List<Method> methods = GetterIsSetterExtractor.getSetter(ExtractorTestClass.class);

        assertNotNull(methods);
        assertEquals(6, methods.size());
    }
}