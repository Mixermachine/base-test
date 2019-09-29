package de.a9d3.testing.testdata;

import de.a9d3.testing.GlobalStatics;
import de.a9d3.testing.method_extractor.GetterIsSetterExtractor;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.IntStream;

// yes unchecked casts from Object to T (T being the target class) are done in this class.
// I have not found a better way to do it.
// If you know a better way, do not hesitate to write an issue on GlobalStatics.GIT_REPO
@SuppressWarnings("unchecked")
public class TestDataProvider {
    protected static final int LIST_ARRAY_ITEM_COUNT = 2;
    private static final Logger LOGGER = Logger.getLogger(TestDataProvider.class.getName());
    private Map<String, Function<String, Object>> providerMap;

    /**
     * This class will invoke classes with seeded data
     */
    public TestDataProvider() {
        providerMap = TestDataStatics.getCompleteSeededMap();
    }

    /**
     * This class will invoke classes with data from a custom map
     *
     * @param customMap The elements in this map will override the keys of the default map
     */
    public TestDataProvider(Map<String, Function<String, Object>> customMap) {
        providerMap = new HashMap<>();
        providerMap.putAll(customMap);
    }

    /**
     * This factory will return a TestDataProvider which uses the provided seeds for generating test data
     *
     * @return A new TestDataProvider with a seeded provider map
     */
    public static TestDataProvider getSeededTestDataProvider() {
        // current default
        return new TestDataProvider(TestDataStatics.getCompleteSeededMap());
    }

    /**
     * This factory will return a TestDataProvider which uses the class defaults for generating test data
     *
     * @return A new TestDataProvider with a default provider map
     */
    public static TestDataProvider getDefaultTestDataProvider() {
        return new TestDataProvider(TestDataStatics.getCompleteDefaultMap());
    }

    private static void writeWarningLogCouldNotInitialize(Class c) {
        // Could not initialize class
        StringBuilder message = new StringBuilder();
        message.append("Could not initialize ");
        message.append(c.getName());

        if (c.getConstructors().length == 0) {
            message.append("\nClass has no constructors.\nPlease refer to ");
            message.append(GlobalStatics.TEST_DATA_PROVIDER_WIKI);
            message.append(" to get an idea how to use customMaps to initialize the TestDataProvider");
        }

        LOGGER.warning(message::toString);
    }

    private static Constructor[] getSortedConstructors(Class c, boolean tryComplexConstructorFirst) {
        Constructor[] constructors = c.getConstructors();
        Arrays.sort(constructors, tryComplexConstructorFirst ? // tryComplexConstructorFirst determines the sorted order
                Comparator.comparingInt(con -> -con.getParameterCount()) :
                Comparator.comparingInt(Constructor::getParameterCount));
        return constructors;
    }

    /**
     * This method will try to invoke the provided class.
     *
     * @param c                          The class which should be invoked
     * @param seed                       The seed which should be used to generate the constructor parameters
     * @param tryComplexConstructorFirst If true, try largest constructor first
     * @param <T>                        Return type
     * @return Initialized object
     */
    public <T> T fill(Class c, String seed, boolean tryComplexConstructorFirst) {
        Function<String, Object> fun = providerMap.get(c.getName());
        if (fun != null) {
            return (T) fun.apply(seed);
        } else {
            LOGGER.finest("Could not find class in functionMap. Trying to invoke class by constructors.");
            return decideWhichKindOfComplexClassIsNeeded(c, seed, tryComplexConstructorFirst);
        }
    }

    protected <T> T decideWhichKindOfComplexClassIsNeeded(Class c, String seed, boolean complex) {
        if (Collection.class.isAssignableFrom(c)) {
            return (T) invokeCollectionInstance(c, seed);
        } else if (Map.class.isAssignableFrom(c)) {
            return (T) invokeMapInstance(c, seed);
        } else if (c.isArray()) {
            return invokeArrayInstance(c, seed);
        } else {  // something else, try to invoke directly via constructor
            return invokeComplexClass(c, seed, complex);
        }
    }

    /**
     * This method will invoke the right kind of array object for your array class
     *
     * @param c    An array class
     * @param seed The seed which should be used for generating seeded data
     * @return An instance of the provided class
     */
    private <T> T invokeArrayInstance(Class c, String seed) {
        Object objects = Array.newInstance(c.getComponentType(), LIST_ARRAY_ITEM_COUNT);
        for (int i = 0; i < Array.getLength(objects); i++) {
            Array.set(objects, i, fill(c.getComponentType(), seed + i, false));
        }

        return (T) objects;
    }

    /**
     * This method will invoke the right kind of collection object for your collection class
     *
     * @param c    A class which inherits collection
     * @param seed The seed which should be used for generating seeded data
     * @return An instance of the provided class
     */
    private Collection invokeCollectionInstance(Class c, String seed) {
        Collection instance;

        // https://static.javatpoint.com/images/java-collection-hierarchy.png
        if (c.equals(List.class) || c.equals(Queue.class)) {
            instance = new LinkedList<>();
        } else if (c.equals(Set.class)) {
            instance = new HashSet<>();
        } else {
            instance = invokeComplexClass(c, seed, false);
        }

        if (instance != null) {
            for (int i = 0; i < LIST_ARRAY_ITEM_COUNT; i++) {
                instance.add(null);
            }
        }

        return instance;
    }

    /**
     * This method will invoke the right kind of map object for your map class
     *
     * @param c    A class which inherits map
     * @param seed The seed which should be used for generating seeded data
     * @return An instance of the provided class
     */
    private Map invokeMapInstance(Class c, String seed) {
        Map instance;

        // https://static.javatpoint.com/images/core/java-map-hierarchy.png
        if (!c.equals(SortedMap.class) && c.equals(Map.class)) {
            instance = new HashMap();
            instance.put(null, null);
        } else if (c.equals(SortedMap.class) || c.equals(TreeMap.class)) { // TreeMap as it does not allow null values
            instance = new TreeMap();
        } else {
            instance = invokeComplexClass(c, seed, false);

            if (instance != null) {
                instance.put(null, null);
            }
        }

        return instance;
    }

    /**
     * This method will initialize the provided class with null pointer for the mutable variables and random
     * values for the immutable variables (can't be null)
     *
     * @param c   Class which should be initialized
     * @param <T> Return type
     * @return Initialized class
     * @throws IllegalAccessException    is thrown if the access to the class, field, method or constructor is not allowed.
     * @throws InvocationTargetException is thrown when the called method throws an exception.
     */
    public <T> T fillMutableWithNull(Class c) throws IllegalAccessException, InvocationTargetException {
        Object instance = fill(c, "123", false);

        for (Method method : GetterIsSetterExtractor.getSetter(c)) {
            if (!method.getParameterTypes()[0].isPrimitive()) {
                method.invoke(instance, new Object[1]);
            }
        }

        return (T) instance;
    }

    /**
     * Adds custom mappings to the internal provider map.
     * Duplicate entries will be overwritten.
     *
     * @param customMap Mapping of data
     */
    public void addCustomMappings(Map<String, Function<String, Object>> customMap) {
        providerMap.putAll(customMap);
    }

    private <T> T invokeComplexClass(Class c, String seed, boolean tryComplexConstructorFirst) {
        for (Constructor constructor : getSortedConstructors(c, tryComplexConstructorFirst)) {
            if (Arrays.stream(constructor.getParameterTypes()).noneMatch(aClass -> aClass.equals(c))) {
                try {
                    return invokeComplexConstructorWithProviderData(seed, tryComplexConstructorFirst, constructor);
                } catch (InvocationTargetException | IllegalAccessException |
                        InstantiationException | IllegalArgumentException e) {
                    LOGGER.fine("Could not initialize constructor " + constructor + " of class " + c.getName() +
                            ". Trying other constructor if available.");
                }
            }
        }

        writeWarningLogCouldNotInitialize(c);

        return null;
    }

    public <T> T invokeComplexConstructorWithProviderData(String seed, boolean tryComplexConstructorFirst,
                                                          Constructor constructor)
            throws InstantiationException, IllegalAccessException, InvocationTargetException {
        // try create a arguments array which can be used to invoke the constructor
        Object[] args = IntStream.range(0, constructor.getParameterCount())
                .mapToObj(i -> fill(constructor.getParameterTypes()[i], seed + i, tryComplexConstructorFirst))
                .toArray();

        return (T) (args.length == 0 ? constructor.newInstance() : constructor.newInstance(args));
    }
}
