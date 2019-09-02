package de.a9d3.testing.resource_classes;

import java.util.Set;

public class TestDataProviderTestClass {
    private short a;
    private String b;
    private Integer c;
    private Boolean d;
    private Set e;

    public TestDataProviderTestClass() {
        // simple constructor
    }

    public TestDataProviderTestClass(short a, String b, Integer c, Boolean d, Set e) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }

    public short getA() {
        return a;
    }

    public void setA(short a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public Integer getC() {
        return c;
    }

    public void setC(Integer c) {
        this.c = c;
    }

    public Boolean getD() {
        return d;
    }

    public void setD(Boolean d) {
        this.d = d;
    }

    public Set getE() {
        return e;
    }

    public void setE(Set e) {
        this.e = e;
    }
}
