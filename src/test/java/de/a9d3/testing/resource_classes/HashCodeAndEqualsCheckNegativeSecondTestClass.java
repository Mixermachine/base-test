package de.a9d3.testing.resource_classes;

import java.util.Objects;

public class HashCodeAndEqualsCheckNegativeSecondTestClass {
    private String a;
    private String b;
    private int c;
    private char d;

    public String getA() {
        return a;
    }

    public void setA(String a) {
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

    public char getD() {
        return d;
    }

    public void setD(char d) {
        this.d = d;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashCodeAndEqualsCheckNegativeSecondTestClass that = (HashCodeAndEqualsCheckNegativeSecondTestClass) o;
        return getC() == that.getC() &&
                getD() == that.getD() &&
                Objects.equals(getA(), that.getA()) &&
                Objects.equals(getB(), that.getB());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getA(), getB(), getD());
    } // hashcode does not include all variables
}
