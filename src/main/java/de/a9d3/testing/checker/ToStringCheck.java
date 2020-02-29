package de.a9d3.testing.checker;

import de.a9d3.testing.testdata.TestDataProvider;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ToStringCheck implements CheckerInterface {
    private static final Logger LOGGER = Logger.getLogger(ToStringCheck.class.getName());

    private TestDataProvider provider;
    private String regexExcluded;
    private String seed;

    public ToStringCheck() {
        this("");
    }

    public ToStringCheck(String regexExcluded) {
        this(new TestDataProvider(), regexExcluded);
    }

    public ToStringCheck(TestDataProvider testDataProvider, String regexExcluded) {
        this(testDataProvider, regexExcluded, "720f7f56-12fa-47cf-9a16-ea9c38675b74");
    }

    public ToStringCheck(TestDataProvider testDataProvider, String regexExcluded, String seed) {
        this.provider = testDataProvider;
        this.regexExcluded = regexExcluded;
        this.seed = seed;
    }

    private static List<Field> getMatchingFields(Class c, String regexExcluded) {
        Pattern pattern = Pattern.compile(regexExcluded);

        return Arrays.stream(c.getDeclaredFields())
                .filter(x -> !pattern.matcher(x.getName()).matches())
                .collect(Collectors.toList());
    }

    @Override
    public boolean check(Class c) throws ReflectiveOperationException {
        return check(c, getMatchingFields(c, regexExcluded));
    }

    public boolean check(Class c, List<Field> fields) throws ReflectiveOperationException {
        Method toStringMethod = c.getMethod("toString");
        Object initializedClass = provider.fill(c, seed, true);
        Object resultObject = toStringMethod.invoke(initializedClass);

        if (!(resultObject instanceof String)) {
            LOGGER.severe("ToString method did not return object of type String");
            return false;
        }

        String resultString = (String) resultObject;

        for (Field field : fields) {
            field.setAccessible(true);

            Object fieldValue = field.get(initializedClass);

            if (!resultString.contains(fieldValue.toString())) {
                LOGGER.severe("Field: " + field.getName() + " was not included in String. " +
                        "Field value: " + fieldValue + ", toStringValue: " + resultString);
                return false;
            }
        }

        return true;
    }
}
