package de.a9d3.testing.checks;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.logging.Logger;

public class PublicVariableCheck implements CheckInterface {
    private static final Logger LOGGER = Logger.getLogger(PublicVariableCheck.class.getName());

    private Boolean allowStaticFinalPublicVariables;

    /**
     * Openly accessible variables can make the internal state of an object unpredictable and may lead to well
     * hidden bugs. Hide variables with getter/setters where possible and employ checks in these methods to make sure,
     * your object is functional after the execution.
     * This checkClass will check if any public variables are presented.
     * Initialize empty or with allowStaticFinalPublicVariables true to allow public static final variables.
     */
    public PublicVariableCheck() {
        this(false);
    }

    public PublicVariableCheck(Boolean allowStaticFinalPublicVariables) {
        this.allowStaticFinalPublicVariables = allowStaticFinalPublicVariables;
    }

    @Override
    public boolean check(Class c) {
        return Arrays.stream(c.getDeclaredFields()).noneMatch(
                this::checkIfFieldDoesExposesPublicVariablesAndFinalStatic);

    }

    private boolean checkIfFieldDoesExposesPublicVariablesAndFinalStatic(Field field) {
        if (Modifier.isPublic(field.getModifiers())) {
            LOGGER.fine(() -> field + " is public.");

            if (!(Modifier.isFinal(field.getModifiers()) &&
                    Modifier.isStatic(field.getModifiers()) &&
                    allowStaticFinalPublicVariables)) {
                CheckHelperFunctions.logFailedCheckStep(LOGGER, field,
                        "field is nonFinal or/and and nonStatic.");

                return true;
            }
        }
        return false;
    }
}
