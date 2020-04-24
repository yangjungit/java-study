package com.giovanny.study.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @packageName: com.example.demo1.aspect
 * @className: ScheduleLockAdvice
 * @description:
 * @author: YangJun
 * @date: 2020/4/20 14:52
 * @version: v1.0
 **/
@Slf4j
@Aspect
@Component
public class ScheduleLockAdvice {


    /**
     * 定义切点  切点名就是方法名
     */
    @Pointcut("execution(public * com.example.demo1.schedule..*(..))")
    public void scheduleLockPointCut() {
    }

    /**
     * 环绕通知需要携带ProceedingJoinPoint类型的参数
     * 环绕通知类似于动态代理的全过程：ProceedingJoinPoint类型的参数可以决定是否执行目标方法。
     * 而且环绕通知必须有返回值，返回值即为目标方法的返回值
     *
     * @return obj
     */
    @Around(value = "scheduleLockPointCut()")
    public Object aroundMethod(ProceedingJoinPoint pjp) {


        return null;
    }

}
