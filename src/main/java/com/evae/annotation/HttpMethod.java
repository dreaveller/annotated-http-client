package com.evae.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 被此注解修饰的方法，可选传入HttpRequestHeader,HttpRequestQuery,HttpRequestBody，参数会自动匹配。
 * 如果传入的是String，则需要按照(header,query,body)的顺序传入。
 * 不需要在注解中指定返回值类型，会自动返回对应的类型。
 * @author evae
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpMethod {
    String path() default "";
    String method() default "GET";
}
