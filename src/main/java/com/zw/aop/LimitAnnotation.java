package com.zw.aop;


import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LimitAnnotation {
    // resource key, different API use different rate limitation
    String key() default "";

    // max requests per second
    double perSecond();

    // timeout when acquire token
    long timeout();
    TimeUnit timeunit() default TimeUnit.SECONDS;

    // message when didn't acquire token successfully
    String msg() default "service is busy, try again later";

}
