# 注解方式http调用
### 使用方式

```java
@HttpClient(domain = "http://localhost:8080")
public interface TestClient {
    @HttpMethod(path = "/test")
    byte[] test();
    
    @HttpMethod(path = "/test")
    String test(HttpRequestHeader header, String content);

    @HttpClient(path = "/test")
    Future<String> testAsync(HttpRequestHeader header, String content);

    @HttpClient(path = "/test", method = "POST")
    Future<String> testAsync(HttpRequestHeader header, String content);
}

public class Test {
    public static void main(String[] args) {
        TestClient client = HttpClientFactory.create(TestClient.class);
        client.test(new HttpRequestHeader(), "test");
        client.testAsync(new HttpRequestHeader(), "test");
    }
}
```