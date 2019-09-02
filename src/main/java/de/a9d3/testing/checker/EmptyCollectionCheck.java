package de.a9d3.testing.checker;


import de.a9d3.testing.method_extractor.GetterIsSetterExtractor;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

public class EmptyCollectionCheck implements CheckerInterface {

    @Override
    public boolean check(Class c) throws ReflectiveOperationException  {
        Object instance = c.newInstance();

        return GetterIsSetterExtractor.getGetter(c).stream()
                .filter(getter ->
                        checkIfListOrMap(getter.getReturnType())).noneMatch(getter -> {
                    try {
                        return getter.invoke(instance) == null;
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    return true;
                });
    }

    private boolean checkIfListOrMap(Class m) {
        return Collection.class.isAssignableFrom(m) || Map.class.isAssignableFrom(m);
    }
}
