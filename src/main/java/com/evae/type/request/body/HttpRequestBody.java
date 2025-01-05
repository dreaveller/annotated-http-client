package com.evae.type.request.body;

public interface HttpRequestBody<T> {
    T getBody();
    void setBody(T body);
}
