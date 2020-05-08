package de.a9d3.testing.checks;

import de.a9d3.testing.testdata.TestDataProvider;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ToStringCheck implements CheckInterface {
    private static final Logger LOGGER = Logger.getLogger(ToStringCheck.class.getName());

    private final TestDataProvider provider;
    private final String regexExcluded;
    private final String seed;

    public ToStringCheck() {
        this("");
    }

    public ToStringCheck(String regexExcluded) {
        this(new TestDataProvider(), regexExcluded);
    }

    public ToStringCheck(TestDataProvider provider) {
        this(provider, "");
    }

    public ToStringCheck(TestDataProvider testDataProvider, String regexExcluded) {
        this(testDataProvider, regexExcluded, "720f7f56-12fa-47cf-9a16-ea9c38675b74");
    }

    public ToStringCheck(TestDataProvider testDataProvider, String regexExcluded, String seed) {
        this.provider = testDataProvider;
        this.regexExcluded = regexExcluded;
        this.seed = seed;
    }

    private static List<Field> getMatchingFields(Class<?> c, String regexExcluded) {
        Pattern pattern = Pattern.compile(regexExcluded);

        return Arrays.stream(c.getDeclaredFields())
                .filter(x -> !x.isSynthetic()) // need to ignore synthetic fields like $jacocoData
                .filter(x -> !pattern.matcher(x.getName()).matches())
                .collect(Collectors.toList());
    }

    @Override
    public boolean check(Class<?> c) {
        return check(c, getMatchingFields(c, regexExcluded));
    }

    public boolean check(Class<?> c, List<Field> fields) {
        Method toStringMethod;
        try {
            toStringMethod = c.getMethod("toString");
        } catch (NoSuchMethodException e) {
            LOGGER.severe("Failed to get toString method. Exception: " + e);
            return false;
        }
        Object initializedClass = provider.fill(c, seed, true);

        if (initializedClass == null) {
            LOGGER.severe(() -> "Class could not be initialized.");
            return false;
        }

        Object resultObject;
        try {
            resultObject = toStringMethod.invoke(initializedClass);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.severe("Failed to invoke toString method. Exception e: " + e);
            return false;
        }

        String resultString = (String) resultObject;

        for (Field field : fields) {
            field.setAccessible(true);

            Object fieldValue;
            try {
                fieldValue = field.get(initializedClass);
            } catch (IllegalAccessException e) {
                LOGGER.severe("Could not access inner field. Exception e:" + e);
                return false;
            } finally {
                field.setAccessible(false);
            }

            if (!resultString.contains(fieldValue.toString())) {
                LOGGER.severe(() -> "Field: " + field.getName() + " was not included in String. " +
                        "Field value: " + fieldValue + ", toStringValue: " + resultString);
                return false;
            }
        }

        return true;
    }
}
