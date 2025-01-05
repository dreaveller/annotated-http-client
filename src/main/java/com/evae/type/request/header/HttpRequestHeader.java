package com.evae.type.request.header;

import java.util.function.BiConsumer;

public interface HttpRequestHeader {
    void forEach(BiConsumer<String, String> action);
}
