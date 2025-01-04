package com.evae;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpClientFactory {

    private static final Map<Class<?>, Object> CLIENT_MAP = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> clazz) {
        if (CLIENT_MAP.containsKey(clazz)) {
            return (T) CLIENT_MAP.get(clazz);
        }

        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new HttpClientProxy());
        CLIENT_MAP.put(clazz, o);
        return (T) o;
    }

}
