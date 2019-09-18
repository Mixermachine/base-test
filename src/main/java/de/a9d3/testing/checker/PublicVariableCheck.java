package de.a9d3.testing.checker;

import de.a9d3.testing.checker.exception.CheckerHelperFunctions;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.logging.Logger;

public class PublicVariableCheck implements CheckerInterface {
    private static final Logger LOGGER = Logger.getLogger(PublicVariableCheck.class.getName());

    private Boolean allowStaticFinalPublicVariables;

    public PublicVariableCheck() {
        this(false);
    }

    public PublicVariableCheck(Boolean allowStaticFinalPublicVariables) {
        this.allowStaticFinalPublicVariables = allowStaticFinalPublicVariables;
    }

    @Override
    public boolean check(Class c) {
        return Arrays.stream(c.getDeclaredFields()).noneMatch(field -> {
            if (Modifier.isPublic(field.getModifiers())) {
                LOGGER.fine(() -> field + " is public.");

                if (!(Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers()) &&
                        allowStaticFinalPublicVariables)) {
                    CheckerHelperFunctions.logFailedCheckerStep(LOGGER, field,
                            "field is nonFinal or/and and nonStatic.");

                    return true;
                }
            }

            return false;
        });

    }
}
