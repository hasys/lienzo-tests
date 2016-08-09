package com.ait.lienzo.test.powermock;

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Rectangle;

public class Foo {

    public boolean isConstructorCalled = false;

    public Foo() {
        isConstructorCalled = true;
    }

    public int getSomeInt() {
        return 5;
    }

    public String getSomeNoneStaticString() {
        return "None static";
    }

    public static String getSomeString() {
        return "Test String";
    }

    private Layer layer;
    private final Rectangle rectangle = new Rectangle( 50, 50 );

    public void setLayer(Layer layer) {
        this.layer = layer;
        someMethod();
    }

    protected void someMethod() {

    }

    public void test() {
        rectangle.setFillColor( "#0000FF" );
        layer.add( rectangle );
        layer.draw();
    }

    public void test2() {
        final int w = layer.getWidth();
        rectangle.setX( w + 100 );
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
