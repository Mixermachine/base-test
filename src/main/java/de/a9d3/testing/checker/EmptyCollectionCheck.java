package de.a9d3.testing.checker;


import de.a9d3.testing.GlobalStatics;
import de.a9d3.testing.checker.exception.CheckerHelperFunctions;
import de.a9d3.testing.method_extractor.GetterIsSetterExtractor;
import de.a9d3.testing.testdata.TestDataProvider;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

public class EmptyCollectionCheck implements CheckerInterface {
    private static final Logger LOGGER = Logger.getLogger(EmptyCollectionCheck.class.getName());

    private TestDataProvider provider;

    public EmptyCollectionCheck() {
        this(new TestDataProvider());
    }

    public EmptyCollectionCheck(TestDataProvider provider) {
        this.provider = provider;
    }

    private static boolean checkIfListOrMap(Class m) {
        return Collection.class.isAssignableFrom(m) || Map.class.isAssignableFrom(m);
    }

    @Override
    public boolean check(Class c) {
        try {
            Object instance = provider.fillMutableWithNull(c);

            return GetterIsSetterExtractor.getGetter(c).stream()
                    .filter(getter -> checkIfListOrMap(getter.getReturnType()))
                    .noneMatch(getter -> {
                        try {
                            boolean returnedNull = getter.invoke(instance) == null;

                            if (returnedNull) {
                                CheckerHelperFunctions.logFailedCheckerStep(LOGGER, getter,
                                        "Returned null instead of empty object.");
                            }

                            return returnedNull;
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            CheckerHelperFunctions.logFailedCheckerStep(LOGGER, getter, e);
                        }

                        return true;
                    });
        } catch (IllegalAccessException | InvocationTargetException e) {
            CheckerHelperFunctions.logFailedCheckerStep(LOGGER, c,
                    "Could not initialize class with internal null variables. You might need a custom " +
                            "TestDataProvider. See " + GlobalStatics.TEST_DATA_PROVIDER_WIKI, e);
        }

        return false;
    }
}
