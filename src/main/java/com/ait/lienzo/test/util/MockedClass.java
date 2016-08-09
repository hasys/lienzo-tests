package com.ait.lienzo.test.util;

public class MockedClass {

    private String className;
    private String methodName;

    public MockedClass(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!(other instanceof MockedClass)) {
            return false;
        }

        MockedClass o = (MockedClass) other;
        return this.className.equals(o.className) && this.methodName.equals(o.methodName);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash += className.hashCode() * 3;
        hash += methodName.hashCode() * 5;
        return hash;
    }
}
