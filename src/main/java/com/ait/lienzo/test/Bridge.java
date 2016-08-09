package com.ait.lienzo.test;

import java.util.Map;

import com.ait.lienzo.test.util.MockedClass;

public class Bridge {

    private static boolean staticMocked = false;
    private static MockedClass calledMethod = null;
    private static Map<MockedClass, Object> mockedCalls = new java.util.HashMap();

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
        return mockedCalls.get(new MockedClass(className, methodName));
    }

    public static void methodCalled(String className, String methodName) {
        calledMethod = new MockedClass(className, methodName);
    }

    public static boolean isMethodPrepared() {
        return calledMethod != null;
    }

    public static Object getInvoked() {
        return calledMethod;
    }

    public static void whenInvoked(MockedClass calledMethod, Object value) {
        mockedCalls.put(calledMethod, value);
    }
}
