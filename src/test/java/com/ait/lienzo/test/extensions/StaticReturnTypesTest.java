package com.ait.lienzo.test.extensions;

import static com.ait.lienzo.test.LienzoMockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.ait.lienzo.test.Bridge;
import com.ait.lienzo.test.LienzoMockitoTestRunner;
import com.ait.lienzo.test.annotation.PrepareForTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(LienzoMockitoTestRunner.class)
// TODO: auto reset mockClasses between testClasses
@PrepareForTest({Foo.class, Bar.class})
public class StaticReturnTypesTest {

    @Before
    public void setUp() {
        // TODO: auto reset mocks between methods
        Bridge.resetMocks();
    }

    @Test
    public void testMockMethodsWithPrimitiveBooleanReturnValues() {
        assertTrue(Bar.getStaticPrimitiveBoolean());

        Bridge.mockStatic(Bar.class);
        assertFalse(Bar.getStaticPrimitiveBoolean());

        when(Bar.getStaticPrimitiveBoolean()).thenReturn(true);
        assertTrue(Bar.getStaticPrimitiveBoolean());
        when(Bar.getStaticPrimitiveBoolean()).thenReturn(false);
        assertFalse(Bar.getStaticPrimitiveBoolean());
    }

    @Test
    public void testMockMethodsWithPrimitiveIntReturnValues() {
        assertEquals(7, Bar.getStaticPrimitiveInt());

        Bridge.mockStatic(Bar.class);
        assertEquals(0, Bar.getStaticPrimitiveInt());

        when(Bar.getStaticPrimitiveInt()).thenReturn(-9);
        assertEquals(-9, Bar.getStaticPrimitiveInt());
        when(Bar.getStaticPrimitiveInt()).thenReturn(15);
        assertEquals(15, Bar.getStaticPrimitiveInt());
    }

    @Test
    public void testStaticOverriding() {
        assertEquals("Test String", Foo.getSomeString());
        assertEquals("Test String: Hello0", Foo.getSomeString("Hello", 0));

        Bridge.mockStatic(Foo.class);
        assertEquals(null, Foo.getSomeString());
        assertEquals(null, Foo.getSomeString("Hello", 1));

        when(Foo.getSomeString()).thenReturn("Mocked Value");
        assertEquals("Mocked Value", Foo.getSomeString());
        assertEquals(null, Foo.getSomeString("Hello", 2));

        when(Foo.getSomeString("", 3)).thenReturn("Mocked!3");
        assertEquals("Mocked!3", Foo.getSomeString("Hello", 3));
    }

    @Test
    public void testMockStaticVoidMethods() {
        assertFalse(Bar.isVoidMethodCalled);
        Bar.someVoidMethod();
        assertTrue(Bar.isVoidMethodCalled);

        Bar.resetStaticValues();
        assertFalse(Bar.isVoidMethodCalled);

        Bridge.mockStatic(Bar.class);
        Bar.someVoidMethod();
        assertFalse(Bar.isVoidMethodCalled);
    }

    @Test
    @Ignore
    public void testDifferentCallsOnOneMethod() {
        Bridge.mockStatic(Foo.class);
        when(Foo.getSomeString("World", 5)).thenReturn("Mocked!5");
        assertEquals("Mocked!5", Foo.getSomeString("Hello", 5));
        when(Foo.getSomeString("Hello", 6)).thenReturn("Hello Mocked!6");
        assertEquals("Hello Mocked!6", Foo.getSomeString("Hello", 6));
        assertEquals("Mocked!5", Foo.getSomeString("Any other string", 5));
    }
}
