package de.a9d3.testing.testdata;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;

public class TestDataProvider {
    private static final int LIST_ITEM_COUNT = 2;

    private Map<String, Function<String, Object>> providerMap;

    public TestDataProvider() {
        this(Collections.emptyMap());
    }

    public TestDataProvider(Map<String, Function<String, Object>> customMap) {
        providerMap = getDefaultProviderMap();

        providerMap.putAll(customMap);
    }

    public static Map<String, Function<String, Object>> getDefaultProviderMap() {
        Map<String, Function<String, Object>> map = new HashMap<>();

        map.put(boolean.class.getName(), x -> (x.hashCode() % 2 != 0));
        map.put(Boolean.class.getName(), map.get(boolean.class.getName()));

        map.put(char.class.getName(), x -> (char) (x.hashCode() % Character.MAX_VALUE));
        map.put(Character.class.getName(), map.get(char.class.getName()));

        map.put(byte.class.getName(), x -> (byte) (x.hashCode() % (Byte.MAX_VALUE - Byte.MIN_VALUE) - Byte.MAX_VALUE));
        map.put(Byte.class.getName(), map.get(byte.class.getName()));

        map.put(short.class.getName(), x -> (short) (x.hashCode() %
                (Short.MAX_VALUE - Short.MIN_VALUE) - Short.MAX_VALUE));
        map.put(Short.class.getName(), map.get(short.class.getName()));

        map.put(int.class.getName(), String::hashCode);
        map.put(Integer.class.getName(), map.get(int.class.getName()));

        map.put(long.class.getName(), x -> (long) x.hashCode() << 16);
        map.put(Long.class.getName(), map.get(long.class.getName()));

        map.put(float.class.getName(), x -> ((float) x.hashCode()) / 3);
        map.put(Float.class.getName(), map.get(float.class.getName()));

        map.put(double.class.getName(), x -> ((double) x.hashCode()) * 2 / 3);
        map.put(Double.class.getName(), map.get(double.class.getName()));

        map.put(String.class.getName(), x -> UUID.nameUUIDFromBytes(x.getBytes()).toString());

        // Other classes
        map.put(Instant.class.getName(), x -> Instant.ofEpochSecond(x.hashCode()));

        return map;
    }

    public <T> T fill(Class type, String seed, Boolean tryComplexConstructorIfPossible) {
        Function<String, Object> fun = providerMap.get(type.getName());
        if (fun != null) {
            return (T) fun.apply(seed);
        } else {
            return generateTestDataByNonStandardClass(type, seed, tryComplexConstructorIfPossible);
        }
    }

    public <T> T generateTestDataByNonStandardClass(Class x, String seed, Boolean complex) {
        if (Collection.class.isAssignableFrom(x)) {
            return (T) invokeCollectionInstance(x, seed);
        } else if (Map.class.isAssignableFrom(x)) {
            return (T) invokeMapInstance(x, seed);
        } else {
            return  (resolveComplexObject(x, seed, complex));
        }
    }

    private Collection invokeCollectionInstance(Class c, String seed) {
        Collection instance = null;

        // https://static.javatpoint.com/images/java-collection-hierarchy.png
        if (c.equals(List.class) || c.equals(Queue.class)) {
            instance = new LinkedList<>();
        } else if (c.equals(Set.class)) {
            instance = new HashSet<>();
        } else {
            instance = resolveComplexObject(c, seed, false);
        }

        for (int i = 0; i < LIST_ITEM_COUNT; i++) {
            instance.add(null);
        }

        return instance;
    }

    private Map invokeMapInstance(Class c, String seed) {
        Map instance = null;

        // https://static.javatpoint.com/images/core/java-map-hierarchy.png
        if (!c.equals(SortedMap.class) && c.equals(Map.class)) {
            instance = new HashMap();
            instance.put(null, null);
        } else if (c.equals(SortedMap.class) || c.equals(TreeMap.class)){ // TreeMap as it does not allow null values
            instance = new TreeMap();
        } else {
            instance = resolveComplexObject(c, seed,false);
            instance.put(null, null);
        }

        return instance;
    }

    private <T> T resolveComplexObject(Class c, String seed, Boolean tryComplexConstructor) {
        Constructor[] constructors = c.getConstructors();
        if (tryComplexConstructor) {
            Arrays.sort(constructors, Comparator.comparingInt(con -> -con.getParameterCount()));
        } else {
            Arrays.sort(constructors, Comparator.comparingInt(Constructor::getParameterCount));
        }

        for (Constructor single : constructors) {
            if (Arrays.stream(single.getParameterTypes()).noneMatch(aClass -> aClass.equals(c))) {
                try {
                    Object[] args = new Object[single.getParameterCount()];
                    for (int i = 0; i < single.getParameterCount(); i++) {
                        args[i] = fill(single.getParameterTypes()[i], seed + i, tryComplexConstructor);
                    }

                    if (args.length == 0) {
                        return (T) single.newInstance();
                    } else {
                        return (T) single.newInstance(args);
                    }
                } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
                    // suppress exceptions
                }
            }
        }

        return null;
    }
}
