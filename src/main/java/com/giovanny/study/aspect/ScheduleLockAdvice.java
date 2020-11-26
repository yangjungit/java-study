package com.giovanny.study.aspect;

import com.giovanny.study.annotation.RedisLockAnnotation;
import com.giovanny.study.service.RedisLockService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @packageName: com.giovanny.study.aspect
 * @className: ScheduleLockAdvice
 * @description: 切面 切点  aop
 * @author: YangJun
 * @date: 2020/4/24 15:04
 * @version: v1.0
 **/
@Slf4j
@Aspect
@Component
public class ScheduleLockAdvice {

    @Autowired
    private RedisLockService redisLockService;

    /**
     * 定义切点  切点名就是方法名
     * 方法内部不用实现 返回为void
     */
    @Pointcut("execution(public * com.giovanny.study.schedule..*(..))")
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
    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
        //获取方法
        Method method = ((MethodSignature) (pjp.getSignature())).getMethod();
        //获取到指定类型的注解
        RedisLockAnnotation annotation = method.getAnnotation(RedisLockAnnotation.class);
        //判断有没有这个注解
        if (annotation == null) {
            //没有加指定注解通过反射执行方法
            Object proceed = pjp.proceed();
            log.info("method:[{}]没有加[@RedisLockAnnotation]注解,目标方法返回[{}]", method.getName(), proceed);
            return proceed;
        } else {
            //加了指定注解
            //方法名
            String lockName = annotation.lockName();
            if (StringUtils.isEmpty(lockName)) {
                String methodName = method.getName();
                String classReferenceName = ScheduleLockAdvice.class.getName();
                lockName = classReferenceName + ":" + methodName;
            }
            //获取锁
            boolean lock = redisLockService.scheduleLock(lockName, annotation.holdTimeMillis());
            if (lock) {
                log.info("[{}]获取到锁", lockName);
                // 这里执行完以后可以主动释放锁（直接删除锁） 可以用try-finally在finally里面释放锁 也可以判断pjp.proceed()返回释放锁，但是这个有涉及业务了
                return pjp.proceed();
            } else {
                log.info("[{}]没有获取到锁,此次不执行！", lockName);
                return null;
            }
        }
    }

}
