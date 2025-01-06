package com.evae.definition;

import com.evae.annotation.HttpClient;
import com.evae.annotation.HttpMethod;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class MethodSignature {
    private final Type genericReturnType;
    private final Class<?> returnType;
    private final Type[] genericParameterTypes;
    private final Class<?>[] parameterTypes;
    /**
     * millis
     */
    private final int timeout;
    private final String domain;
    private final String path;
    private final String method;

    public MethodSignature(Method method) {
        HttpMethod methodAnnotation = method.getAnnotation(HttpMethod.class);
        HttpClient interfaceAnnotation = method.getDeclaringClass().getAnnotation(HttpClient.class);

        genericReturnType = method.getGenericReturnType();
        returnType = method.getReturnType();
        genericParameterTypes = method.getGenericParameterTypes();
        parameterTypes = method.getParameterTypes();
        timeout = methodAnnotation.timeout();
        domain = interfaceAnnotation.domain();
        path = methodAnnotation.path();
        this.method = methodAnnotation.method();
    }

    public String getUrl() {
        return (domain.endsWith("/") ? domain : domain + "/") + (path.startsWith("/") ? path.substring(1) : path);
    }



    public Type getGenericReturnType() {
        return genericReturnType;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public Type[] getGenericParameterTypes() {
        return genericParameterTypes;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getPath() {
        return path;
    }

    public String getDomain() {
        return domain;
    }

    public String getMethod() {
        return method;
    }
}
