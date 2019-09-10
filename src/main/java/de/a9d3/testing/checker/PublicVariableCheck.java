package de.a9d3.testing.checker;

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
    public boolean check(Class c) {
        return Arrays.stream(c.getDeclaredFields()).noneMatch(field ->
                Modifier.isPublic(field.getModifiers()) &&
                        !(Modifier.isFinal(field.getModifiers()) &&
                                Modifier.isStatic(field.getModifiers()) &&
                                allowStaticFinalPublicVariables));
    }
}
