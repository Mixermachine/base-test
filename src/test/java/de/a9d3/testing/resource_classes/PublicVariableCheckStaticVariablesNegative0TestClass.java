package de.a9d3.testing.resource_classes;

public class PublicVariableCheckStaticVariablesNegative0TestClass {
    public static final String niceString = "Clean cood is nice";
    public static final Boolean happyDeveloper = true; // this is bad

    public String a;
    private int b;

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
}
