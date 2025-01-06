package com.evae.type.request.query;

import java.util.Map;

public class HttpRequestQueryImpl implements HttpRequestQuery {

    private Map<String, String> queryParameters;

    public void addQuery(String key, String value) {
        queryParameters.put(key, value);
    }

    public String getQuery(String key) {
        return queryParameters.get(key);
    }

    public void removeQuery(String key) {
        queryParameters.remove(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : queryParameters.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return sb.toString();
    }
}
