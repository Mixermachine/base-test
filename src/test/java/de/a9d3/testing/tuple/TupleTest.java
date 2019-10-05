package de.a9d3.testing.tuple;

import de.a9d3.testing.checks.EmptyCollectionCheck;
import de.a9d3.testing.checks.GetterIsSetterCheck;
import de.a9d3.testing.checks.HashcodeAndEqualsCheck;
import de.a9d3.testing.checks.PublicVariableCheck;
import de.a9d3.testing.executer.SingleThreadExecutor;
import de.a9d3.testing.testdata.TestDataProvider;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.junit.Assert.assertTrue;

public class TupleTest {

    @Test
    public void baseTest() {
        SingleThreadExecutor executor = new SingleThreadExecutor();
        Map<String, Function<String, Object>> customMap = new HashMap<>();
        customMap.put(Object.class.getName(), s -> s);  // use provided string directly so we get differentiable objects
        TestDataProvider provider = new TestDataProvider(customMap);

        assertTrue(executor.execute(Tuple.class, Arrays.asList(
                new EmptyCollectionCheck(provider), new GetterIsSetterCheck(provider),
                new HashcodeAndEqualsCheck(provider), new PublicVariableCheck())));
    }

    @Test
    public void toStringTest() {
        String a = "11f4a59a-f960-4b94-94fc-88bed52f28f9";
        String b = "9f1d03bd-138e-4ea1-8dd0-d134bd7e09b9";

        Tuple<String, String> myTuple = new Tuple<>(a, b);

        assertTrue(myTuple.toString().contains(a));
        assertTrue(myTuple.toString().contains(b));
    }
}