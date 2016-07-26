package com.ait.lienzo.test.powermock;

//import static com.ait.lienzo.test.LienzoMockito.when;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.test.LienzoMockitoTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.MockGateway;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith(LienzoMockitoTestRunner.class)
@PrepareForTest(Foo.class)
public class PowermockTest {

    @Mock
    private Foo someMock;

    private Foo realFoo;

    @Mock
    private Layer layer;

    @Test
    public void testPowermock() throws Throwable {
        mockStatic(Foo.class);
        when(MockGateway.methodCall(Foo.class, "getSomeString", new Object[0], new Class[0], "java.lang.String")).thenReturn("Hello World");
        assertEquals("Hello World", MockGateway.methodCall(Foo.class, "getSomeString", new Object[0], new Class[0], "java.lang.String"));
    }

    @Test
    public void testConstructor() throws Throwable {
        when(someMock.getSomeInt()).thenReturn(15);
        whenNew(Foo.class).withAnyArguments().thenReturn(someMock);
        Object o = MockGateway.newInstanceCall(Foo.class, new Object[0], new Class[0]);
        Foo f = (Foo)o;
        assertEquals(15, f.getSomeInt());
        assertFalse(f.isConstructorCalled);
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
    }
}
