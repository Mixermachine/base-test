package de.a9d3.testing.checker;

import de.a9d3.testing.method.GetterSetterMatcher;
import de.a9d3.testing.method.IsSetterMatcher;
import de.a9d3.testing.method.MethodMatcherInterface;
import de.a9d3.testing.testdata.TestDataProvider;
import de.a9d3.testing.tuple.MethodTuple;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetterIsSetterCheck implements CheckerInterface {
    private static final Logger LOGGER = Logger.getLogger(GetterIsSetterCheck.class.getName());

    private TestDataProvider provider;
    private String regexExcluded;
    private String seed;

    public GetterIsSetterCheck() {
        this("");
    }

    public GetterIsSetterCheck(String regexExcluded) {
        this(new TestDataProvider(), regexExcluded);
    }

    public GetterIsSetterCheck(TestDataProvider provider) {
        this(provider, "");
    }

    public GetterIsSetterCheck(TestDataProvider provider, String regexExcluded) {
        this(provider, regexExcluded, "48107951-0256-4a84-9f8e-132ee651ae9e");
    }

    public GetterIsSetterCheck(TestDataProvider provider, String regexExcluded, String seed) {
        this.provider = provider;
        this.regexExcluded = regexExcluded;
        this.seed = seed;
    }

    @Override
    public boolean check(Class c) {
        MethodMatcherInterface getterSetterMatcher = new GetterSetterMatcher();
        MethodMatcherInterface isSetterMatcher = new IsSetterMatcher();

        Pattern pattern = Pattern.compile(regexExcluded);

        List<MethodTuple> tuples = Stream
                .concat(getterSetterMatcher.match(c).stream(), isSetterMatcher.match(c).stream())
                .filter(tuple -> !(pattern.matcher(tuple.getA().getName()).matches() &&
                        pattern.matcher(tuple.getB().getName()).matches()))
                .collect(Collectors.toList());

        return check(c, tuples);
    }

    public boolean check(Class c, List<MethodTuple> tuples) {
        Object instance = provider.fill(c, "test", true);

        for (int i = 0; i < tuples.size(); i++) {
            if (checkIfGetterReturnSetValue(tuples.get(i), instance, i))
                return false;
        }

        return true;
    }

    private boolean checkIfGetterReturnSetValue(MethodTuple tuple, Object instance, int i) {
        Object data = provider.fill(tuple.getB().getParameterTypes()[0], seed + i, true);

        try {
            tuple.getB().invoke(instance, data);

            if (!data.equals(tuple.getA().invoke(instance))) {
                CheckerHelperFunctions.logFailedCheckerStep(LOGGER, tuple,
                        "Getter return value did not match previously set value.");

                return true;
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            CheckerHelperFunctions.logFailedCheckerStep(LOGGER, tuple, "Failed to invoke. See exception.");

            return true;
        }
        return false;
    }
}
