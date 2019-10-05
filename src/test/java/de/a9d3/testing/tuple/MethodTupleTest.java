package de.a9d3.testing.tuple;

import de.a9d3.testing.checks.EmptyCollectionCheck;
import de.a9d3.testing.checks.GetterIsSetterCheck;
import de.a9d3.testing.checks.HashcodeAndEqualsCheck;
import de.a9d3.testing.checks.PublicVariableCheck;
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

        Method[] dummyMethods = this.getClass().getMethods();
        customMap.put(Method.class.getName(), s -> dummyMethods[0]);

        // due to type erasure we only see type of the generic extended class in hashcodeAndEqualsCheck,
        // thus simply insert Method object here
        customMap.put(Object.class.getName(), s -> dummyMethods[0]);
        TestDataProvider provider = new TestDataProvider(customMap);

        // no defensive copying as tuple should be lightweight
        assertTrue(executor.execute(MethodTuple.class, Arrays.asList(
                new EmptyCollectionCheck(provider), new GetterIsSetterCheck(provider),
                new HashcodeAndEqualsCheck(provider), new PublicVariableCheck())));
    }
}