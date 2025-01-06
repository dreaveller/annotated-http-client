package com.evae.type.request;

import com.evae.type.request.body.HttpRequestBody;
import com.evae.type.request.header.HttpRequestHeader;
import com.evae.type.request.query.HttpRequestQuery;

public class HttpRequest<T> {
    private HttpRequestHeader header;
    private HttpRequestQuery query;
    private HttpRequestBody<T> body;

    public void addHeader() {

    }

    public HttpRequestHeader getHeader() {
        return header;
    }

    public void setHeader(HttpRequestHeader header) {
        this.header = header;
    }

    public HttpRequestQuery getQuery() {
        return query;
    }

    public void setQuery(HttpRequestQuery query) {
        this.query = query;
    }

    public HttpRequestBody<T> getBody() {
        return body;
    }

    public void setBody(HttpRequestBody<T> body) {
        this.body = body;
    }
}
