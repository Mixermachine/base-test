package de.a9d3.testing.resource_classes;

import java.util.*;

public class EmptyCollectionPositiveTestClass {
    private List<String> a;
    private Map<String, String> b;

    public List<String> getA() {
        return a != null ? a : Collections.emptyList();
    }

    public void setA(List<String> a) {
        this.a = a != null ? a : new ArrayList<>();
    }

    public Map<String, String> getB() {
        return b != null ? b : Collections.emptyMap();
    }

    public void setB(Map<String, String> b) {
        this.b = b != null ? b : new HashMap<>();
    }
}
