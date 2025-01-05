package com.evae.type.request.body;

public class HttpRequestBodyString implements HttpRequestBody<String> {
    private String body;

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public void setBody(String body) {
        this.body = body;
    }
}
