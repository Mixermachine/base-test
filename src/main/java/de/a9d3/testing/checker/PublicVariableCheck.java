package de.a9d3.testing.checker;

import de.a9d3.testing.checker.exception.MismatchException;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class PublicVariableCheck implements CheckerInterface {

    private Boolean allowStaticFinalPublicVariables;

    public PublicVariableCheck() {
        this(false);
    }

    public PublicVariableCheck(Boolean allowStaticFinalPublicVariables) {
        this.allowStaticFinalPublicVariables = allowStaticFinalPublicVariables;
    }

    @Override
    public boolean check(Class c) throws MismatchException {
        return Arrays.stream(c.getDeclaredFields()).noneMatch(field ->
                Modifier.isPublic(field.getModifiers()) &&
                        !(Modifier.isFinal(field.getModifiers()) &&
                                Modifier.isStatic(field.getModifiers()) &&
                                allowStaticFinalPublicVariables));
    }
}
