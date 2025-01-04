package com.evae.type;

import java.util.function.BiConsumer;

public interface HttpRequestHeader {
    void forEach(BiConsumer<String, String> action);
}
