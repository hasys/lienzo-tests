package com.ait.lienzo.test.util;

import com.ait.lienzo.test.Bridge;
import org.mockito.stubbing.Answer;

public class OngoingStubbing<T> implements org.mockito.stubbing.OngoingStubbing {

    private T stub = null;

    public void setStub(T stub) {
        this.stub = stub;
    }

    @Override public org.mockito.stubbing.OngoingStubbing thenReturn(Object value) {
        Bridge.whenInvoked((MockedClass) stub, value);
        return this;
    }

    @Override public org.mockito.stubbing.OngoingStubbing thenReturn(Object value, Object[] values) {
        return this;
    }

    @Override public org.mockito.stubbing.OngoingStubbing thenThrow(Throwable... throwables) {
        return this;
    }

    @Override public org.mockito.stubbing.OngoingStubbing thenCallRealMethod() {
        return this;
    }

    @Override public Object getMock() {
        return stub;
    }

    @Override public org.mockito.stubbing.OngoingStubbing then(Answer answer) {
        return this;
    }

    @Override public org.mockito.stubbing.OngoingStubbing thenAnswer(Answer answer) {
        return this;
    }

    @Override public org.mockito.stubbing.OngoingStubbing thenThrow(Class[] throwableClasses) {
        return this;
    }
}
