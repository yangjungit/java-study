package com.giovanny.study.aspect;

import com.giovanny.study.annotation.ExpireType;
import com.giovanny.study.annotation.LuaRedisAcquire;
import com.giovanny.study.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Collections;

/**
 * @packageName: com.giovanny.study.aspect
 * @className: FlowAcquireAdvice
 * @description: 限流切面
 * @author: YangJun
 * @date: 2020/4/26 10:38
 * @version: v1.0
 **/
@Aspect
@Component
@Slf4j
public class FlowAcquireAdvice {

    @Autowired
    private RedisUtil redisUtil;

    @Resource(name = "acquireScript")
    private RedisScript<Boolean> redisScript;

    /**
     * 定义一个切点
     */
    @Pointcut("execution(public * *(..)) && @annotation(com.giovanny.study.annotation.LuaRedisAcquire)")
    public void acquirePointCut() {
    }

    @Around(value = "acquirePointCut()")
    public Object acquireAround(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        LuaRedisAcquire annotation = method.getAnnotation(LuaRedisAcquire.class);
        if (annotation == null) {
            return pjp.proceed();
        } else {
            String key = annotation.key();
            String expire = annotation.expire();
            String token = annotation.token();
            String expireType = annotation.ExType();
            Boolean flag = redisUtil.acquire(redisScript, Collections.singletonList(key), token, expire, expireType);
            if (flag) {
                return pjp.proceed();
            } else {
                log.info("获取token：false。");
            }
        }
        return null;
    }

}
