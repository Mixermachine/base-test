package de.a9d3.testing.checks;

import de.a9d3.testing.method_extractor.GetterIsSetterExtractor;
import de.a9d3.testing.testdata.TestDataProvider;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class CopyConstructorCheck implements CheckInterface {
    private static final Logger LOGGER = Logger.getLogger(CopyConstructorCheck.class.getName());

    private final TestDataProvider provider;
    private final boolean ignoreHashcodeAndEquals;

    /**
     * Complex objects sometimes need to be copied (especially when you apply defensive copying) and thus we create
     * copyConstructors. These constructors are often forgotten when it comes to refactoring/adding new variables to
     * the class.
     * This checkClass will check if the copyConstructors copies all objects correctly
     * (needs equals methods at least in the classes of the inner variables).
     * Initialize empty, with custom TestDataProvider and/or ignoreHashcodeAndEquals boolean
     * (disables hashcode and equals check)
     */
    public CopyConstructorCheck() {
        this(new TestDataProvider(), false);
    }

    public CopyConstructorCheck(TestDataProvider provider) {
        this(provider, false);
    }

    public CopyConstructorCheck(boolean ignoreHashcodeAndEquals) {
        this(new TestDataProvider(), ignoreHashcodeAndEquals);
    }

    public CopyConstructorCheck(TestDataProvider provider, boolean ignoreHashcodeAndEquals) {
        this.provider = provider;
        this.ignoreHashcodeAndEquals = ignoreHashcodeAndEquals;
    }

    @Override
    public boolean check(Class<?> c) {
        Constructor<?> copyConstructor;

        try {
            copyConstructor = c.getConstructor(c);
        } catch (NoSuchMethodException e) {
            CheckHelperFunctions.logFailedCheckStep(LOGGER, c, "No copy constructor found", e);

            return false;
        }

        Object a;
        try {
            a = fillClassWithAsMuchDataAsPossible(c);
        } catch (InvocationTargetException | IllegalAccessException e) {
            CheckHelperFunctions.logFailedCheckStep(LOGGER, c, "Failed to fill object of class.", e);

            return false;
        }

        Object b;
        try {
            b = copyConstructor.newInstance(a);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            CheckHelperFunctions.logFailedCheckStep(LOGGER, c, "Failed to invoke copy constructor.", e);

            return false;
        }

        try {
            if (!compareObjectOnGetterIs(a, b)) {
                return false;
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            CheckHelperFunctions.logFailedCheckStep(LOGGER, c, "Failed to compare object ");

            return false;
        }

        if (!ignoreHashcodeAndEquals) {
            if (!a.equals(b)) {
                CheckHelperFunctions.logFailedCheckStep(LOGGER, c,
                        "Copied object is not equal to original object.");

                return false;
            }

            if (a.hashCode() != b.hashCode()) {
                CheckHelperFunctions.logFailedCheckStep(LOGGER, c,
                        "Hashcode of copied object did not equal the hashcode of original object");

                return false;
            }
        }

        return true;
    }

    private Object fillClassWithAsMuchDataAsPossible(Class<?> c) throws InvocationTargetException, IllegalAccessException {
        Object obj = provider.fill(c, "ce47ddd6-315c-4687-867c-95be31afe9b5", false);

        int iter = 0;

        for (Method setter : GetterIsSetterExtractor.getSetter(c)) {
            CheckHelperFunctions.executeSetter(provider, setter, obj, iter++);
        }

        return obj;
    }

    private boolean compareObjectOnGetterIs(Object a, Object b)
            throws InvocationTargetException, IllegalAccessException {
        for (Method getter : GetterIsSetterExtractor.getGetterAndIs(a.getClass())) {
            Object aReturn = getter.invoke(a);
            Object bReturn = getter.invoke(b);

            if (aReturn == null || !aReturn.equals(bReturn)) {
                CheckHelperFunctions.logFailedCheckStep(LOGGER, getter,
                        "Getter did not return same value");


                return false;
            }
        }

        return true;
    }
}
