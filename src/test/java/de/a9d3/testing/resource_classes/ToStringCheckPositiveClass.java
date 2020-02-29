package de.a9d3.testing.resource_classes;

import java.util.List;

public class ToStringCheckPositiveClass {
    private String a;
    private Integer b;
    private Object c;
    private Thread d;
    private int e;
    private List f;

    public ToStringCheckPositiveClass(String a, Integer b, Object c, Thread d, int e, List f) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }

    @Override
    public String toString() {
        return "ToStringCheckPositiveClass{" + "a='" + a + '\'' +
                ", b=" + b +
                ", c=" + c +
                ", d=" + d +
                ", e=" + e +
                ", f=" + f +
                '}';
    }
}
