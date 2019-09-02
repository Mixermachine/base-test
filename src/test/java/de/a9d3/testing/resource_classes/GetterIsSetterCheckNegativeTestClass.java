package de.a9d3.testing.resource_classes;

public class GetterIsSetterCheckNegativeTestClass {
    String a;
    int b;
    short c;
    Integer d;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public short getC() {
        return c;
    }

    public void setC(short c) {
        this.c = c;
    }

    public Integer getD() {
        return d;
    }

    public void setD(Integer d) {
        this.b = d; // malicious assignment. This should be detected by getterIsSetterCheck
    }
}
