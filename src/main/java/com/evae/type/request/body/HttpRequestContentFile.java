package com.evae.type.request.body;

import java.io.File;

public class HttpRequestContentFile implements HttpRequestBody<File> {

    private File body;

    @Override
    public File getBody() {
        return body;
    }

    @Override
    public void setBody(File body) {
        this.body = body;
    }

}
