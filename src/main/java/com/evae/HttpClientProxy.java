package com.evae;

import com.evae.definition.MethodSignature;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HttpClientProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isDefault()) {
            throw new IllegalStateException("default implementation is not supported");
        } else {
            return HttpClientProcessor.invoke(new MethodSignature(method), args);
        }
    }

}
