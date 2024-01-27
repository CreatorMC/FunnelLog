package com.creator.funnellog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DoRateLimiter {
    //超时时长
    double permitsPerSecond() default 0;
    //拦截返回信息（Json）
    String returnJson() default "";
}
