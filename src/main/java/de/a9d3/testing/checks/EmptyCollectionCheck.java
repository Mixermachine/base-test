package de.a9d3.testing.checks;


import de.a9d3.testing.GlobalStatics;
import de.a9d3.testing.method_extractor.GetterIsSetterExtractor;
import de.a9d3.testing.testdata.TestDataProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

public class EmptyCollectionCheck implements CheckInterface {
    private static final Logger LOGGER = Logger.getLogger(EmptyCollectionCheck.class.getName());

    private final TestDataProvider provider;

    /**
     * When for example working with lists in objects a calling program has to check for null, emptyList and list
     * with a payload. We cut away the null pointer check if the object never returns a null pointer for a collection
     * (edge cases exist where a null pointer is truly wanted).
     * This checkClass will collect all getter methods with a collection as return type returns no null pointer.
     * Initialize empty or with custom TestDataProvider
     */
    public EmptyCollectionCheck() {
        this(new TestDataProvider());
    }

    public EmptyCollectionCheck(TestDataProvider provider) {
        this.provider = provider;
    }

    private static boolean checkIfListOrMap(Class<?> m) {
        return Collection.class.isAssignableFrom(m) || Map.class.isAssignableFrom(m);
    }

    private static boolean checkIfCollectionReturnsNull(Object instance, Method getter) {
        try {
            boolean returnedNull = getter.invoke(instance) == null;

            if (returnedNull) {
                CheckHelperFunctions.logFailedCheckStep(LOGGER, getter,
                        "Returned null instead of empty object.");
            }

            return returnedNull;
        } catch (IllegalAccessException | InvocationTargetException e) {
            CheckHelperFunctions.logFailedCheckStep(LOGGER, getter, e);
        }

        return true;
    }

    @Override
    public boolean check(Class<?> c) {
        try {
            Object instance = provider.fillMutableWithNull(c);

            return GetterIsSetterExtractor.getGetter(c).stream()
                    .filter(getter -> checkIfListOrMap(getter.getReturnType()))
                    .noneMatch(getter -> checkIfCollectionReturnsNull(instance, getter));
        } catch (IllegalAccessException | InvocationTargetException e) {
            CheckHelperFunctions.logFailedCheckStep(LOGGER, c,
                    "Could not initialize class with internal null variables. You might need a custom " +
                            "TestDataProvider. See " + GlobalStatics.TEST_DATA_PROVIDER_WIKI, e);
        }

        return false;
    }
}
