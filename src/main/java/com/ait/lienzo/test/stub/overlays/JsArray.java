/*
 *
 *    Copyright (c) 2014,2015,2016 Ahome' Innovation Technologies. All rights reserved.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *  
 */

package com.ait.lienzo.test.stub.overlays;

import com.ait.lienzo.test.annotation.StubClass;
import com.google.gwt.core.client.JavaScriptObject;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory array implementation stub for class <code>com.google.gwt.core.client.JsArray</code>.
 * 
 * @author Roger Martinez
 * @since 1.0
 * 
 */
@StubClass( "com.google.gwt.core.client.JsArray" )
public class JsArray<T extends JavaScriptObject> extends JavaScriptObject {

    private final List<T> list = new ArrayList<T>();
    
    protected JsArray() {
    }

    public T get(int index) {
        return list.get( index );
    }

    public String join() {
        return join(",");
    }

    public String join(String separator) {
        return StringUtils.join( list, separator);
    }

    public int length() {
        return list.size();
    }

    public void push(T value) {
        list.add( value );
    }

    public void set(int index, T value) {
        list.set( index, value );
    }

    public void setLength(int newLength) {
        // Does not makes sense here.
    }

    public T shift() {
        return doShift();
    }

    public void unshift(T value) {
        doUnShift( value );
    }

    @SuppressWarnings("unchecked")
    private T doShift() {
        T t = (T) list.get( 0 );
        list.remove( 0 );
        return t;
    }

    private void doUnShift( final T value ) {
        list.add( 0, value );
    }
    
}
