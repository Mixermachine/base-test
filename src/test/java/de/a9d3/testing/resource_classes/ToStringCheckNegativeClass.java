package de.a9d3.testing.resource_classes;

import java.util.List;

public class ToStringCheckNegativeClass {
    private String a;
    private Integer b;
    private Object c;
    private Thread d;
    private int e;
    private List f;

    public ToStringCheckNegativeClass(String a, Integer b, Object c, Thread d, int e, List f) {
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
                //sb.append(", d=").append(d); d is not appended to return String
                ", e=" + e +
                ", f=" + f +
                '}';
    }
}
