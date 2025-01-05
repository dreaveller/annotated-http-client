package com.evae.type.request.body;

public class HttpRequestBodyByteArray implements HttpRequestBody<byte[]> {
    private byte[] body;

    @Override
    public byte[] getBody() {
        return body;
    }

    @Override
    public void setBody(byte[] body) {
        this.body = body;
    }
}
