package de.a9d3.testing.tuple;

import java.lang.reflect.Method;
import java.util.Objects;

public class MethodTuple extends Tuple<Method, Method> {
    public MethodTuple(Method a, Method b) {
        this.setA(a);
        this.setB(b);
    }

    @Override
    public String toString() {
        return "MethodTuple{" +
                "a=" + this.getA() +
                ", b=" + this.getB() +
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
