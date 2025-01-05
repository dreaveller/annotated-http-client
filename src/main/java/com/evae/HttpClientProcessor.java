package com.evae;

import com.evae.definition.HttpMethodDefinition;
import com.evae.type.request.body.HttpRequestBody;
import com.evae.type.request.header.HttpRequestHeader;
import com.evae.type.HttpRequestQuery;
import com.evae.type.request.header.HttpRequestHeaderImpl;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class HttpClientProcessor {

    private static final HttpClient httpClient = HttpClient.newHttpClient();

    public static Object invoke(HttpMethodDefinition definition, Method method, Object[] args) throws URISyntaxException, IOException, InterruptedException {
        HttpRequestHeader header = null;
        String content = null;
        HttpRequestQuery query = null;
        HttpRequestBody<?> body = null;

        if (args == null) {
            return invoke(definition, method, header, content);
        } else if (args.length == 1) {
            if (args[0] instanceof HttpRequestHeader) {
                header = (HttpRequestHeader) args[0];
            } else if (args[0] instanceof String) {
                content = (String) args[0];
            } else if (args[0] instanceof HttpRequestQuery) {
                query = (HttpRequestQuery) args[0];
            } else if (args[0] instanceof HttpRequestBody<?>) {
                body = (HttpRequestBody<?>) args[0];
            }
            return invoke(definition, method, header, content);
        } else {
            if (args[0] instanceof HttpRequestHeader) {
                header = (HttpRequestHeader) args[0];
            }
            if (args[1] instanceof String) {
                content = (String) args[1];
            }
            return invoke(definition, method, header, content);
        }
    }

    public static Object invoke2(HttpMethodDefinition definition, Method method, Object[] args) throws URISyntaxException, IOException, InterruptedException {
        HttpRequestHeader header = null;
        HttpRequestQuery query = null;
        HttpRequestBody<?> body = null;

        if (args == null) {
            return invoke(definition, method, header, (String) body.getBody());
        } else if (args.length > 0) {
            for (Object arg : args) {
                if (arg instanceof HttpRequestHeader) {
                    header = (HttpRequestHeader) arg;
                } else if (arg instanceof HttpRequestQuery) {
                    query = (HttpRequestQuery) arg;
                } else if (arg instanceof HttpRequestBody<?>) {
                    body = (HttpRequestBody<?>) arg;
                }
            }
            for (Object arg : args) {
                if (arg instanceof String) {
                    if (header == null) {
                        header = new HttpRequestHeaderImpl();
                    }
                }
            }
        }
        return null;
    }

    public static Object invoke(HttpMethodDefinition definition, Method method, HttpRequestHeader header, String content) throws URISyntaxException, IOException, InterruptedException {

        if (method.getGenericReturnType() instanceof ParameterizedType type) {
            if (!Future.class.isAssignableFrom((Class<?>) type.getRawType())) {
                throw new IllegalStateException("Return type must be Future");
            }
            Type actualTypeArgument = type.getActualTypeArguments()[0];
            if (!(actualTypeArgument instanceof Class<?>)) {
                throw new IllegalStateException("Generic type must be Class");
            }
            if (String.class.isAssignableFrom((Class<?>) actualTypeArgument)) {
                CompletableFuture<HttpResponse<String>> responseFuture = processHttpAsync(definition, HttpResponse.BodyHandlers.ofString(), header, content);
                return responseFuture.thenApply(HttpResponse::body);
            } else {
                CompletableFuture<HttpResponse<byte[]>> responseFuture = processHttpAsync(definition, HttpResponse.BodyHandlers.ofByteArray(), header, content);
                return responseFuture.thenApply(HttpResponse::body);
            }
        } else {
            if (String.class.isAssignableFrom(method.getReturnType())) {
                HttpResponse<String> response = processHttp(definition, HttpResponse.BodyHandlers.ofString(), header, content);
                return response.body();
            } else {
                HttpResponse<byte[]> response = processHttp(definition, HttpResponse.BodyHandlers.ofByteArray(),header, content);
                return response.body();
            }
        }

    }

    private static <T> HttpResponse<T> processHttp(HttpMethodDefinition definition, HttpResponse.BodyHandler<T> bodyHandler, HttpRequestHeader header, String content) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = buildRequest(definition, header, content);
        return httpClient.send(request, bodyHandler);
    }

    private static <T> CompletableFuture<HttpResponse<T>> processHttpAsync(HttpMethodDefinition definition, HttpResponse.BodyHandler<T> bodyHandler, HttpRequestHeader header, String content) throws URISyntaxException {
        HttpRequest request = buildRequest(definition, header, content);
        return httpClient.sendAsync(request, bodyHandler);
    }

    private static HttpRequest buildRequest(HttpMethodDefinition definition, HttpRequestHeader header, String content) throws URISyntaxException {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(new URI(definition.url))
                .method(definition.method, content != null ? HttpRequest.BodyPublishers.ofString(content) : HttpRequest.BodyPublishers.noBody())
                .timeout(Duration.of(10000, ChronoUnit.MILLIS));
        if (header != null) {
            header.forEach(builder::header);
        }
        return builder.build();
    }

}
