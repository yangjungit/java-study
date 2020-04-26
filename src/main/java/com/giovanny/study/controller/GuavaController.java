package com.giovanny.study.controller;

import com.giovanny.study.entity.MyResponse;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @packageName: com.giovanny.study.controller
 * @className: GuavaController
 * @description: Google guava限流 有代码侵入，用起来不咋方便
 * @author: YangJun
 * @date: 2020/4/26 16:24
 * @version: v1.0
 **/
@RestController
@Slf4j
public class GuavaController {


    /**
     * 允许限流组件每秒发放的令牌数
     */
    RateLimiter rateLimiter = RateLimiter.create(2);


    /**
     * 队列式的请求 一直等到有token为止
     *
     * @param count count
     * @return MyResponse
     */
    @GetMapping("/tryAcquire")
    public MyResponse tryAcquire(Integer count) {
        //桶内令牌是否够
        if (rateLimiter.tryAcquire(count)) {
            log.info("success rate is {}", rateLimiter.getRate());
            return MyResponse.success();
        } else {
            log.info("fail rate is {}", rateLimiter.getRate());
            return MyResponse.failed(1001, "failed");
        }
    }

    /**
     * 队列式的请求 等到有token且不操作等待时间为止
     *
     * @param count   count
     * @param timeout timeout
     * @return MyResponse
     */
    @GetMapping("/tryAcquireTime")
    public MyResponse tryAcquireTime(Integer count, Integer timeout) {
        //桶内令牌是否够
        if (rateLimiter.tryAcquire(count, timeout, TimeUnit.SECONDS)) {
            log.info("success rate is {}", rateLimiter.getRate());
            return MyResponse.success();
        } else {
            log.info("fail rate is {}", rateLimiter.getRate());
            return MyResponse.failed(1001, "failed");
        }
    }

    /**
     * 请求一次，有就有，没有就没有
     *
     * @param count count
     * @return MyResponse
     */
    @GetMapping("/acquire")
    public MyResponse acquire(Integer count) {
        double d = rateLimiter.acquire(count);
        log.info("success rate is {} , return is {}", rateLimiter.getRate(), d);
        return MyResponse.success();
    }
}
