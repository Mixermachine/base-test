package de.a9d3.testing.method;

import java.lang.reflect.Method;
import java.util.Objects;

public class MethodTuple {
    private Method a;
    private Method b;

    public MethodTuple() {
        // empty constructor
    }

    public MethodTuple(Method a, Method b) {
        this.a = a;
        this.b = b;
    }

    public Method getA() {
        return a;
    }

    public void setA(Method a) {
        this.a = a;
    }

    public Method getB() {
        return b;
    }

    public void setB(Method b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "MethodTuple{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodTuple that = (MethodTuple) o;
        return Objects.equals(getA(), that.getA()) &&
                Objects.equals(getB(), that.getB());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getA(), getB());
    }
}
