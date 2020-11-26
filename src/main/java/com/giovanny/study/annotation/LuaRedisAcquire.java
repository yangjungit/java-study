package com.giovanny.study.annotation;

import java.lang.annotation.*;

/**
 * @packageName: com.giovanny.study.annotation
 * @className: LuaRedisAcquire
 * @description: lua + redis 限流
 * @author: YangJun
 * @date: 2020/4/26 9:57
 * @version: v1.0
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LuaRedisAcquire {
    /**
     * redis key
     *
     * @return key
     */
    String key() default "";

    /**
     * 间隔时间内的令牌token数
     * 可以理解成间隔时间内生产token的能力
     * 限流算法：
     * 令牌桶算法 ：这里的token数量相当于桶大小
     * 漏桶算法 ：。。。
     *
     * @return token数量
     */
    String token() default "0";

    /**
     * 间隔时间 失效时间
     * 现在默认1秒
     *
     * @return 时间间隔
     */
    String expire() default "1";

    /**
     * 过期时间单位
     * 秒：SECOND 默认 expire
     * 毫秒：MILLISECOND  pexpire
     *
     * @return 1
     */
    String ExType() default "SECOND";
}
