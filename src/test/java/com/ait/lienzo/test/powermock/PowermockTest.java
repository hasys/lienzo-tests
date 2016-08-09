package com.ait.lienzo.test.powermock;

import static com.ait.lienzo.test.LienzoMockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.test.Bridge;
import com.ait.lienzo.test.LienzoMockitoTestRunner;
import com.ait.lienzo.test.annotation.PrepareForTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

@RunWith(LienzoMockitoTestRunner.class)
@PrepareForTest({Foo.class, Bar.class})
public class PowermockTest {

    @Mock
    private Foo someMock;

    private Foo realFoo;

    @Mock
    private Layer layer;

    @Before
    public void setUp() {
        Bridge.resetMocks();
    }

    @Test
    public void testNormalMock() {
        Foo foo = mock(Foo.class);
        doReturn(3).when(foo).getSomeInt();
        assertEquals(foo.getSomeInt(), 3);
    }

    @Test
    public void testLienzo() {
        when(layer.getWidth()).thenReturn(300);

        realFoo = spy(new Foo());
        realFoo.setLayer(layer);
        realFoo.test();

        verify(realFoo).test();
        verify(realFoo).someMethod();
        verify(layer).add(any(Rectangle.class));
        verify(layer).draw();

        String fColor = realFoo.getRectangle().getFillColor();
        Assert.assertEquals("#0000FF", fColor);

        Bridge.mockStatic(Foo.class);
        assertEquals(null, Foo.getSomeString());
    }

    @Test
    public void testStatic() {
        assertEquals("Test String", Foo.getSomeString());

        Bridge.mockStatic(Foo.class);
        assertEquals(null, Foo.getSomeString());

        when(Foo.getSomeString()).thenReturn("Mocked Value");
        assertEquals("Mocked Value", Foo.getSomeString());
        when(Foo.getSomeString()).thenReturn("Absolutely New Mocked Value");
        assertEquals("Absolutely New Mocked Value", Foo.getSomeString());
    }

    @Test
    @Ignore
    public void testStaticOverriding() {
        assertEquals("Test String", Foo.getSomeString());
        assertEquals("Test String: Hello", Foo.getSomeString("Hello"));

        Bridge.mockStatic(Foo.class);
        assertEquals(null, Foo.getSomeString());
        assertEquals(null, Foo.getSomeString("Hello"));

        when(Foo.getSomeString()).thenReturn("Mocked Value");
        assertEquals("Mocked Value", Foo.getSomeString());
        assertEquals(null, Foo.getSomeString("Hello"));

        when(Foo.getSomeString(anyString())).thenReturn("Mocked!");
        assertEquals("Mocked!", Foo.getSomeString("Hello"));
        when(Foo.getSomeString("Hello")).thenReturn("Hello Mocked!");
        assertEquals("Hello Mocked!", Foo.getSomeString("Hello"));
        assertEquals("Mocked!", Foo.getSomeString("Any other string"));
    }

    @Test
    public void testNonStatic() {
        Foo foo = new Foo();
        assertEquals("None static", foo.getSomeNoneStaticString());
        Bridge.mockStatic(Foo.class);
        assertEquals("None static", foo.getSomeNoneStaticString());

        foo = mock(Foo.class);
        assertEquals(null, foo.getSomeNoneStaticString());
        Bridge.mockStatic(Foo.class);
        when(foo.getSomeNoneStaticString()).thenReturn("Some mocked Value");
        assertEquals("Some mocked Value", foo.getSomeNoneStaticString());
    }

    @Test
    @Ignore
    public void testMockStaticVoidMethods() {
        Foo.someVoidMethod();
        assertTrue(Foo.isVoidMethodCalled);

        Foo.resetStaticValues();
        assertFalse(Foo.isVoidMethodCalled);

        Bridge.mockStatic(Foo.class);
        Foo.someVoidMethod();
        assertFalse(Foo.isVoidMethodCalled);
    }

    @Test
    @Ignore
    public void testMockPrimitiveReturnValues() {
        assertEquals(7, Foo.getSomeStaticPrimitiveInt());

        Bridge.mockStatic(Foo.class);
        assertEquals(0, Foo.getSomeStaticPrimitiveInt());

        when(Foo.getSomeStaticPrimitiveInt()).thenReturn(-9);
        assertEquals(-9, Foo.getSomeStaticPrimitiveInt());
        when(Foo.getSomeStaticPrimitiveInt()).thenReturn(15);
        assertEquals(15, Foo.getSomeStaticPrimitiveInt());
    }

    @Test
    public void testSingleClassMock() {
        assertEquals("Test String", Foo.getSomeString());
        assertEquals("Static string", Bar.getStaticString());

        Bridge.mockStatic(Foo.class);

        assertEquals(null, Foo.getSomeString());
        assertEquals("Static string", Bar.getStaticString());

        Bridge.mockStatic(Bar.class);
        assertEquals(null, Bar.getStaticString());

        when(Foo.getSomeString()).thenReturn("hello");
        when(Bar.getStaticString()).thenReturn("world");
        assertEquals("hello", Foo.getSomeString());
        assertEquals("world", Bar.getStaticString());

        Bridge.resetMocks();
        assertEquals("Test String", Foo.getSomeString());
        assertEquals("Static string", Bar.getStaticString());

        Bridge.mockStatic(Foo.class, Bar.class);
        assertEquals(null, Foo.getSomeString());
        assertEquals(null, Bar.getStaticString());
    }
}
