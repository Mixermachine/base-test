package de.a9d3.testing.resource_classes;

import java.util.Map;
import java.util.Objects;

public class CopyConstructorCheckNegativeTestClass {
    private Integer a;
    private Boolean b;
    private boolean c;
    private String d;
    private Map e;

    public CopyConstructorCheckNegativeTestClass() {
        // should be able to be instantiated
    }

    public CopyConstructorCheckNegativeTestClass(CopyConstructorCheckNegativeTestClass other) {
        this.a = other.a;
        this.b = other.b;
        this.c = other.b; // Error in CopyConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CopyConstructorCheckNegativeTestClass that = (CopyConstructorCheckNegativeTestClass) o;
        return c == that.c &&
                Objects.equals(a, that.a) &&
                Objects.equals(b, that.b) &&
                Objects.equals(d, that.d) &&
                Objects.equals(e, that.e);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c, d, e);
    }
}
