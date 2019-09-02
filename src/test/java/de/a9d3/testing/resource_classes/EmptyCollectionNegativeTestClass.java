package de.a9d3.testing.resource_classes;

import java.util.List;
import java.util.Map;

public class EmptyCollectionNegativeTestClass {
    private List<String> a;
    private Map<String, String> b;

    public List<String> getA() {
        return a;
    }

    public void setA(List<String> a) {
        this.a = a;
    }

    public Map<String, String> getB() {
        return b;
    }

    public void setB(Map<String, String> b) {
        this.b = b;
    }
}
