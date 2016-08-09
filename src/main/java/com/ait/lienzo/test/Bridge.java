package com.ait.lienzo.test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ait.lienzo.test.util.MockedClass;

public class Bridge {

    private static Set<String> staticMocked = new HashSet<>();
    private static MockedClass calledMethod = null;
    private static Map<MockedClass, Object> mockedCalls = new java.util.HashMap();

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
