package de.a9d3.testing.resource_classes;

import java.util.List;

public class ExtractorTestClass {
    private boolean a;
    private String b;
    private int c;
    private Short d;
    private List<String> e;
    private boolean f;

    public boolean isA() {
        return a;
    }

    public void setA(boolean a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public Short getD() {
        return d;
    }

    public void setD(Short d) {
        this.d = d;
    }

    public List<String> getE() {
        return e;
    }

    public void setE(List<String> e) {
        this.e = e;
    }

    public boolean isF() {
        return f;
    }

    public void setF(boolean f) {
        this.f = f;
    }

    public String funnyMethod() {
        return b + d;
    }
}
