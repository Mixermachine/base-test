package de.a9d3.testing.method_extractor;

import de.a9d3.testing.resource_classes.ExtractorTestClass;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MethodExtractorTest {

    @Test
    public void extractTest() {
        List<Method> methods = MethodExtractor.extract(ExtractorTestClass.class, "^funny");

        assertNotNull(methods);
        assertEquals(1, methods.size());
        assertEquals("funnyMethod", methods.get(0).getName());
    }
}