package de.a9d3.testing.checker;

import de.a9d3.testing.checker.exception.MismatchException;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class PublicVariableCheck implements CheckerInterface {
    @Override
    public boolean check(Class c) throws ReflectiveOperationException, MismatchException {
        return Arrays.stream(c.getDeclaredFields()).noneMatch(field -> Modifier.isPublic(field.getModifiers()));
    }
}
