package com.evae;

import com.evae.annotation.HttpClient;
import com.evae.annotation.HttpMethod;
import com.evae.definition.HttpMethodDefinition;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HttpClientProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isDefault()) {
            return method.invoke(proxy, args);
        } else {
            return HttpClientProcessor.invoke(build(method), method, args);
        }
    }

    private HttpMethodDefinition build(Method method) {
        HttpMethodDefinition definition = new HttpMethodDefinition();
        String domain = method.getDeclaringClass().getAnnotation(HttpClient.class).domain();
        domain = domain.endsWith("/") ? domain : domain + "/";
        String url = method.getAnnotation(HttpMethod.class).path();
        url = url.startsWith("/") ? url.substring(1) : url;
        definition.url = domain + url;
        definition.method = method.getAnnotation(HttpMethod.class).method();
        return definition;
    }

}
