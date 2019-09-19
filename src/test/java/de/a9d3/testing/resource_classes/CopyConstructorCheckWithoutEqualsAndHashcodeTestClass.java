package de.a9d3.testing.resource_classes;

import java.util.Map;

public class CopyConstructorCheckWithoutEqualsAndHashcodeTestClass {
    private Integer a;
    private Boolean b;
    private boolean c;
    private String d;
    private Map e;

    public CopyConstructorCheckWithoutEqualsAndHashcodeTestClass() {
        // should be able to be instantiated
    }

    public CopyConstructorCheckWithoutEqualsAndHashcodeTestClass(CopyConstructorCheckWithoutEqualsAndHashcodeTestClass other) {
        this.a = other.a;
        this.b = other.b;
        this.c = other.c;
        this.d = other.d;
        this.e = other.e;
    }

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public Boolean getB() {
        return b;
    }

    public void setB(Boolean b) {
        this.b = b;
    }

    public boolean isC() {
        return c;
    }

    public void setC(boolean c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public Map getE() {
        return e;
    }

    public void setE(Map e) {
        this.e = e;
    }
}
