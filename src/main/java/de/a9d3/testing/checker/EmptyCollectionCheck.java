package de.a9d3.testing.checker;


import de.a9d3.testing.method_extractor.GetterIsSetterExtractor;
import de.a9d3.testing.testdata.TestDataProvider;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

public class EmptyCollectionCheck implements CheckerInterface {

    private TestDataProvider provider;

    public EmptyCollectionCheck() {
        this(new TestDataProvider());
    }

    public EmptyCollectionCheck(TestDataProvider provider) {
        this.provider = provider;
    }

    @Override
    public boolean check(Class c) throws ReflectiveOperationException  {
        Object instance = provider.fillMutableWithNull(c);

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
