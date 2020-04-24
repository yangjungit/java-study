package com.giovanny.study.service.impl;

import com.giovanny.study.service.RedisLockService;
import com.giovanny.study.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @packageName: com.example.demo1.service.impl
 * @className: RedisLockServiceImpl
 * @description: RedisLockServiceImpl
 * @author: YangJun
 * @date: 2020/4/20 14:45
 * @version: v1.0
 **/
@Service
public class RedisLockServiceImpl implements RedisLockService {
    @Autowired
    private RedisUtil redisUtil;

    @Override


    public boolean scheduleLock(String lockName, long holdTimeMillis) {
        //如果key不存在就设置，返回true，存在的话不设置，返回true
        return redisUtil.scheduleLock(lockName, holdTimeMillis + System.currentTimeMillis() + "");

    }
}
