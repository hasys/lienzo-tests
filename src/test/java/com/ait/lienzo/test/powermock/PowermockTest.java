package com.ait.lienzo.test.powermock;

import static com.ait.lienzo.test.LienzoMockito.when;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

@RunWith(LienzoMockitoTestRunner.class)
@PrepareForTest(Foo.class)
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

        Bridge.mockStatic();
        assertEquals(null, Foo.getSomeString());
    }

    @Test
    public void testStatic() {
        assertEquals("Test String", Foo.getSomeString());
        Bridge.mockStatic();

        assertEquals(null, Foo.getSomeString());

        when(Foo.getSomeString()).thenReturn("Mocked Value");
        assertEquals("Mocked Value", Foo.getSomeString());

        when(Foo.getSomeString()).thenReturn("Absolutely New Mocked Value");
        assertEquals("Absolutely New Mocked Value", Foo.getSomeString());
    }

}
