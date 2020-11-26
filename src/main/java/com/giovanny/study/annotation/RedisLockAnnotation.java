package com.giovanny.study.annotation;

import java.lang.annotation.*;

/**
 * @packageName: com.example.demo1.annotation
 * @className: RedisLockAnnotation
 * @description: 分布式锁
 * @author: YangJun
 * @date: 2020/4/20 14:14
 * @version: v1.0
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RedisLockAnnotation {

    long holdTimeMillis() default 5000L;

    String lockName() default "";
}
