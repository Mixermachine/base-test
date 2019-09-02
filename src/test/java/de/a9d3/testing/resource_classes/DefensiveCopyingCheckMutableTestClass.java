package de.a9d3.testing.resource_classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefensiveCopyingCheckMutableTestClass {
    private List<String> a;
    private Map<String, String> b;

    public List<String> getA() {
        return a != null ? new ArrayList<>(a) : null;
    }

    public void setA(List<String> a) {
        this.a = a != null ? new ArrayList<>(a) : null;
    }

    public Map<String, String> getB() {
        return b != null ? new HashMap<>(b) : null;
    }

    public void setB(Map<String, String> b) {
        this.b = b != null ? new HashMap<>(b) : null;
    }
}
