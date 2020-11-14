package de.a9d3.testing.testdata;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@SuppressWarnings("package-private")
public final class TestDataStatics {

    private TestDataStatics() {
        // Should not be initialized
    }

    public static Map<String, Function<String, Object>> getDefaultPrimitiveMap() {
        Map<String, Function<String, Object>> map = new HashMap<>();

        map.put(boolean.class.getName(), x -> false);
        map.put(char.class.getName(), x -> (char) 0);
        map.put(byte.class.getName(), x -> (byte) 0);
        map.put(short.class.getName(), x -> (short) 0);
        map.put(int.class.getName(), x -> 0);
        map.put(long.class.getName(), x -> (long) 0);
        map.put(float.class.getName(), x -> (float) 0);
        map.put(double.class.getName(), x -> (double) 0);

        return map;
    }

    /**
     * This method defines a map with functions which use a seed to
     *
     * @return A map with key (string), {@literal (value(Function<String, Object>))}
     */
    public static Map<String, Function<String, Object>> getSeededPrimitiveMap() {
        Map<String, Function<String, Object>> map = new HashMap<>();

        map.put(boolean.class.getName(), x -> (x.hashCode() % 2 != 0));
        map.put(char.class.getName(), x -> (char) (x.hashCode() % Character.MAX_VALUE));
        map.put(byte.class.getName(), x -> (byte) (x.hashCode() % (Byte.MAX_VALUE - Byte.MIN_VALUE) - Byte.MAX_VALUE));
        map.put(short.class.getName(), x -> (short) (x.hashCode() %
                (Short.MAX_VALUE - Short.MIN_VALUE) - Short.MAX_VALUE));
        map.put(int.class.getName(), String::hashCode);
        map.put(long.class.getName(), x -> (long) x.hashCode() << 16);
        map.put(float.class.getName(), x -> ((float) x.hashCode()) / 3);
        map.put(double.class.getName(), x -> ((double) x.hashCode()) * 2 / 3);


        return map;
    }

    // Needs a reference parameter to existing map as it reuses existing primitive definitions
    public static void addPrimitiveWrapperLinksToMap(Map<String, Function<String, Object>> map) {
        map.put(Boolean.class.getName(), x -> map.get(boolean.class.getName()).apply(x));
        map.put(Character.class.getName(), x -> map.get(char.class.getName()).apply(x));
        map.put(Byte.class.getName(), x -> map.get(byte.class.getName()).apply(x));
        map.put(Short.class.getName(), x -> map.get(short.class.getName()).apply(x));
        map.put(Integer.class.getName(), x -> map.get(int.class.getName()).apply(x));
        map.put(Long.class.getName(), x -> map.get(long.class.getName()).apply(x));
        map.put(Float.class.getName(), x -> map.get(float.class.getName()).apply(x));
        map.put(Double.class.getName(), x -> map.get(double.class.getName()).apply(x));
    }

    public static Map<String, Function<String, Object>> getDefaultComplexMap() {
        Map<String, Function<String, Object>> map = new HashMap<>();

        map.put(String.class.getName(), x -> "");
        map.put(Instant.class.getName(), x -> Instant.ofEpochSecond(0));
        map.put(Date.class.getName(), x -> Date.from(Instant.ofEpochSecond(0)));

        return map;
    }

    public static Map<String, Function<String, Object>> getSeededComplexMap() {
        Map<String, Function<String, Object>> map = new HashMap<>();

        map.put(String.class.getName(), x -> UUID.nameUUIDFromBytes(x.getBytes()).toString());
        map.put(Instant.class.getName(), x -> Instant.ofEpochSecond(x.hashCode()));
        map.put(Date.class.getName(), x -> Date.from(Instant.ofEpochSecond(x.hashCode())));

        return map;
    }

    public static Map<String, Function<String, Object>> getCompleteDefaultMap() {
        Map<String, Function<String, Object>> map = getDefaultPrimitiveMap();
        map.putAll(getDefaultComplexMap());

        addPrimitiveWrapperLinksToMap(map);
        return map;
    }

    public static Map<String, Function<String, Object>> getCompleteSeededMap() {
        Map<String, Function<String, Object>> map = getSeededPrimitiveMap();
        map.putAll(getSeededComplexMap());

        addPrimitiveWrapperLinksToMap(map);

        return map;
    }

}
