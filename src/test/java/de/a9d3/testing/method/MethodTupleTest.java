package de.a9d3.testing.method;

import de.a9d3.testing.checker.EmptyCollectionCheck;
import de.a9d3.testing.checker.GetterIsSetterCheck;
import de.a9d3.testing.checker.HashcodeAndEqualsCheck;
import de.a9d3.testing.checker.PublicVariableCheck;
import de.a9d3.testing.executer.SingleThreadExecutor;
import de.a9d3.testing.testdata.TestDataProvider;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.junit.Assert.assertTrue;

public class MethodTupleTest {
    // lets eat our own dog food
    @Test
    public void baseTest() {

        SingleThreadExecutor executor = new SingleThreadExecutor();
        Map<String, Function<String, Object>> customMap = new HashMap<>();

        Method[] dummyMethod = this.getClass().getMethods();
        customMap.put(Method.class.getName(), s -> dummyMethod[0]);
        TestDataProvider provider = new TestDataProvider(customMap);

        // no defensive copying as tuple should be lightweight
        assertTrue(executor.execute(MethodTuple.class, Arrays.asList(
                new EmptyCollectionCheck(provider), new GetterIsSetterCheck(provider),
                new HashcodeAndEqualsCheck(provider), new PublicVariableCheck())));
    }
}