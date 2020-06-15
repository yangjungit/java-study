package com.giovanny.study.service;

/**
 * @packageName: com.example.demo1.service
 * @className: RedisLockService
 * @description:
 * @author: YangJun
 * @date: 2020/4/20 14:44
 * @version: v1.0
 **/
public interface RedisLockService {
    /**
     * 获取锁
     * 现在的实现是将锁的持有时间保存在value中，没有设置key的过期时间
     * 这种方式可以用，但不够优秀，目前没有设置key的过期时间
     * 可以用lua
     *
     * @param lockName       key
     * @param holdTimeMillis 锁持有时间
     * @return 是否持有锁
     */
    boolean scheduleLock(String lockName, long holdTimeMillis);
}
