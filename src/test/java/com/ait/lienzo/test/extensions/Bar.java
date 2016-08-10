package com.ait.lienzo.test.extensions;

public class Bar {

    public static boolean isVoidMethodCalled = false;

    public static void someVoidMethod() {
        isVoidMethodCalled = true;
    }

    public static void resetStaticValues() {
        isVoidMethodCalled = false;
    }

    public static String getStaticString() {
        return "Static string";
    }

    public static int getStaticPrimitiveInt() {
        return 7;
    }

    public static boolean getStaticPrimitiveBoolean() {
        return true;
    }
}
