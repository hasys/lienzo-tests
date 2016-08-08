package com.ait.lienzo.test;

public class Bridge {

    public static boolean staticMocked = false;

    public static void mockStatic() {
        staticMocked = true;
    }

    public static void resetMocks() {
        staticMocked = false;
    }

    public static boolean isStaticMocked() {
        return staticMocked;
    }

    public static Object invokeMethod(String className, String methodName) {
        return "Mocked String";
    }
}
