package com.ait.lienzo.test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ait.lienzo.test.util.MockedClass;

public class Bridge {

    private static Set<String> staticMocked = new HashSet<>();
    private static MockedClass calledMethod = null;
    private static Map<MockedClass, Object> mockedCalls = new java.util.HashMap<>();

    public static void mockStatic(Class classToMock, Class... moreClassesToMock) {
        staticMocked.add(classToMock.getName());

        for (Class clazz : moreClassesToMock) {
            staticMocked.add(clazz.getName());
        }
    }

    public static void resetMocks() {
        staticMocked.clear();
        mockedCalls.clear();
        calledMethod = null;
    }

    public static boolean isStaticMocked(String className) {
        return staticMocked.contains(className);
    }

    public static Object invokeMethod(String className, String methodName, String parameters, String returnType) {
        Object result = mockedCalls.get(new MockedClass(className, methodName, parameters.split(","), returnType));
        if (result == null && returnType.equals("int")) {
            result = 0;
        }
        return result;
    }

    public static void methodCalled(String className, String methodName, String parameters, String returnType) {
        calledMethod = new MockedClass(className, methodName, parameters.split(","), returnType);
    }

    public static boolean isMethodPrepared() {
        return calledMethod != null;
    }

    public static MockedClass getInvoked() {
        return calledMethod;
    }

    public static void whenInvoked(MockedClass calledMethod, Object value) {
        mockedCalls.put(calledMethod, value);
    }
}
