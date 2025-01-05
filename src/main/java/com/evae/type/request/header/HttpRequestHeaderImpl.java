package com.evae.type.request.header;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class HttpRequestHeaderImpl implements HttpRequestHeader {

    private final Map<String, String> headers = new HashMap<>();

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public void removeHeader(String name) {
        headers.remove(name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
        }
        return sb.toString();
    }

    @Override
    public void forEach(BiConsumer<String, String> action) {
        headers.forEach(action);
    }
}
