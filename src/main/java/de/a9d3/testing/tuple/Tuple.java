package de.a9d3.testing.tuple;

import java.util.Objects;

public class Tuple<A, B> {
    private A a;
    private B b;

    public Tuple() {
    }

    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "Tuple{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return Objects.equals(a, tuple.a) &&
                Objects.equals(b, tuple.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}
