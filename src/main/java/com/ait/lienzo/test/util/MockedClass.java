package com.ait.lienzo.test.util;

import java.util.Arrays;

public class MockedClass {

    private String className;
    private String methodName;
    private String[] parameters;
    private String returnType;

    public MockedClass(String className, String methodName, String[] parameters, String returnType) {
        this.className = className;
        this.methodName = methodName;
        this.parameters = parameters;
        this.returnType = returnType;
    }

    public String getReturnType() {
        return returnType;
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
        return this.className.equals(o.className)
                && this.methodName.equals(o.methodName)
                && Arrays.equals(this.parameters, o.parameters);
    }

    @Override
    public int hashCode() {
        int hash = 9;
        hash += className.hashCode() * 7;
        hash += methodName.hashCode() * 5;
        for (String parameter : parameters) {
            hash += parameter.hashCode() * 3;
        }
        return hash;
    }
}
