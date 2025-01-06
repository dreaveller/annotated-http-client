package com.evae;

import com.evae.definition.MethodSignature;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HttpClientProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isDefault()) {
            return method.invoke(proxy, args);
        } else {
            return HttpClientProcessor.invoke(new MethodSignature(method), args);
        }
    }

}
